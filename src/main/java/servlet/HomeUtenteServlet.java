package servlet;

import dao.DaoFactory;
import dao.jpa.JpaDaoFactory;
import dao.model.EventoDao;
import dao.model.IscrizioneDao;
import dao.model.CategoriaDao;
import dao.jpa.JpaEventoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Evento;
import model.Utente;
import model.Categoria;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EventoDao eventoDao;
    private IscrizioneDao iscrizioneDao;

    @Override
    public void init() throws ServletException {
        eventoDao = DaoFactory.getDaoFactory().getEventoDao();
        iscrizioneDao = DaoFactory.getDaoFactory().getIscrizioneDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Utente utente = (Utente) session.getAttribute("user");

        try {
            EntityManager em = JpaDaoFactory.getManager();
            try {
                TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(e) FROM Evento e", Long.class);
                Long count = countQuery.getSingleResult();

                if (count == 0 && eventoDao instanceof JpaEventoDao) {
                    ((JpaEventoDao) eventoDao).insertTestEvent();
                }
            } finally {
                em.close();
            }

            List<Evento> eventiFuturi = eventoDao.getAllEventiFuturi();
            List<Evento> eventiIscritti = iscrizioneDao.getEventiIscritti(utente);

            List<Evento> eventiDisponibili = new ArrayList<>();
            if (eventiFuturi != null) {
                for (Evento evento : eventiFuturi) {
                    boolean iscritto = false;
                    if (eventiIscritti != null) {
                        for (Evento eventoIscritto : eventiIscritti) {
                            if (eventoIscritto.getId().equals(evento.getId())) {
                                iscritto = true;
                                break;
                            }
                        }
                    }
                    if (!iscritto) {
                        eventiDisponibili.add(evento);
                    }
                }
            }


            if (eventiDisponibili != null) {
                // Calcola i posti disponibili per ogni evento disponibile
                for (Evento evento : eventiDisponibili) {
                    long iscritti = iscrizioneDao.countIscrittiPerEvento(evento);
                    evento.setPostiDisponibili((int) (evento.getCapacita() - iscritti));
                }
            }


            if (eventiIscritti != null) {
                for (Evento evento : eventiIscritti) {
                    int iscritti = (int) iscrizioneDao.countIscrittiPerEvento(evento);
                    evento.setPostiDisponibili(evento.getCapacita() - iscritti);
                }
            }

            request.setAttribute("eventiDisponibili", eventiDisponibili != null ? eventiDisponibili : new ArrayList<>());
            request.setAttribute("eventiIscritti", eventiIscritti != null ? eventiIscritti : new ArrayList<>());

            // Carica le categorie e aggiungile alla request
            CategoriaDao categoriaDao = DaoFactory.getDaoFactory().getCategoriaDao();
            List<Categoria> categorie = categoriaDao.getAllCategorie();
            request.setAttribute("categorie", categorie);

            request.getRequestDispatcher("/WEB-INF/jsp/homeutente.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Si è verificato un errore: " + e.getMessage());
        }
    }
}