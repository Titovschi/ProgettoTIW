package servlet;

import dao.DaoFactory;
import dao.model.EventoDao;
import dao.model.IscrizioneDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Categoria;
import model.Evento;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet("/eliminaIscrizione")
public class EliminaIscrizioneServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long eventoId = Long.parseLong(request.getParameter("eventoId"));
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Utente utente = (Utente) session.getAttribute("user");

        IscrizioneDao iscrizioneDao = DaoFactory.getDaoFactory().getIscrizioneDao();
        EventoDao eventoDao = DaoFactory.getDaoFactory().getEventoDao();

        Evento evento = eventoDao.findById(eventoId);

        // Elimina l'iscrizione
        iscrizioneDao.deleteIscrizione(evento, utente);

        // Aggiorna la lista degli eventi a cui l'utente è iscritto
        List<Evento> eventiIscritti = iscrizioneDao.getEventiIscritti(utente);

        // Aggiorna i posti disponibili per gli eventi a cui l'utente è iscritto
        for (Evento ev : eventiIscritti) {
            long iscritti = iscrizioneDao.countIscrittiPerEvento(ev);
            ev.setPostiDisponibili(ev.getCapacita() - (int) iscritti);
        }

        session.setAttribute("eventiIscritti", eventiIscritti);

        // Aggiorna la lista degli eventi disponibili
        List<Evento> eventiDisponibili = eventoDao.getAllEventiFuturi();
        List<Evento> eventiDaRimuovere = new java.util.ArrayList<>();

        // Rimuovi gli eventi a cui l'utente è già iscritto dalla lista degli eventi disponibili
        for (Evento ev : eventiDisponibili) {
            boolean iscritto = false;
            for (Evento evIscritto : eventiIscritti) {
                if (ev.getId().equals(evIscritto.getId())) {
                    iscritto = true;
                    break;
                }
            }
            if (iscritto) {
                eventiDaRimuovere.add(ev);
            }
        }
        eventiDisponibili.removeAll(eventiDaRimuovere);

        // Aggiorna i posti disponibili per ogni evento disponibile
        for (Evento ev : eventiDisponibili) {
            long iscritti = iscrizioneDao.countIscrittiPerEvento(ev);
            ev.setPostiDisponibili(ev.getCapacita() - (int) iscritti);
        }

        List<Categoria> categorie = DaoFactory.getDaoFactory().getCategoriaDao().findAll();
        request.setAttribute("categorie", categorie);

        session.setAttribute("eventiDisponibili", eventiDisponibili);

        request.getRequestDispatcher("/WEB-INF/jsp/homeutente.jsp").forward(request, response);
    }
}