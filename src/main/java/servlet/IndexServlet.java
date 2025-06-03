package servlet;


import dao.DaoFactory;
import dao.model.EventoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Evento;

import java.io.IOException;
import java.util.List;

@WebServlet
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EventoDao eventoDao = DaoFactory.getDaoFactory().getEventoDao();
        List<Evento> eventiDisponibili = eventoDao.getAllEventiFuturi();
        request.setAttribute("eventiDisponibili", eventiDisponibili);
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
