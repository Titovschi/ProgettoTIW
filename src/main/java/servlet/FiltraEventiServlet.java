package servlet;

import dao.DaoFactory;
import dao.model.EventoDao;
import dao.model.IscrizioneDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Categoria;
import model.Evento;
import model.Utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/filtraEventi")
public class FiltraEventiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoriaIdStr = request.getParameter("categoriaId");
        List<Evento> eventiFiltrati = new ArrayList<>();

        EventoDao eventoDao = DaoFactory.getDaoFactory().getEventoDao();

        if (categoriaIdStr != null && !categoriaIdStr.isEmpty()) {
            try {
                Long categoriaId = Long.parseLong(categoriaIdStr);
                for (Evento e : eventoDao.getAllEventiFuturi()) {
                    if (e.getCategoria() != null && Objects.equals(e.getCategoria().getId(), categoriaId)) {
                        eventiFiltrati.add(e);
                    }
                }
            } catch (NumberFormatException ignored) {}
        } else {
            eventiFiltrati = eventoDao.getAllEventiFuturi();
        }

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("user");

        IscrizioneDao iscrizioneDao = DaoFactory.getDaoFactory().getIscrizioneDao();
        List<Evento> eventiIscritti = iscrizioneDao.getEventiIscritti(utente);

        for (Evento evento : eventiFiltrati) {
            long iscritti = iscrizioneDao.countIscrittiPerEvento(evento);
            evento.setPostiDisponibili((int) (evento.getCapacita() - iscritti));
        }

        request.setAttribute("eventiDisponibili", eventiFiltrati);
        request.setAttribute("eventiIscritti", eventiIscritti);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/homeutente.jsp").forward(request, response);
    }
}