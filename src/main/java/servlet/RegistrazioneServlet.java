package servlet;

import dao.DaoFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Utente;

import java.io.IOException;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/registrazione.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Utente nuovoUtente = new Utente();
        nuovoUtente.setUsername(username);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password);

        // Imposta il ruolo in base alla presenza di altri utenti nel DB
        if (DaoFactory.getDaoFactory().getUtenteDao().countUtenti() == 0) {
            nuovoUtente.setRuolo("admin");
        } else {
            nuovoUtente.setRuolo("utente");
        }

        boolean isRegistrato = DaoFactory.getDaoFactory().getUtenteDao().register(nuovoUtente);

        if (isRegistrato) {
            request.setAttribute("msg", "Registrazione completata. Effettua il login.");
            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", "Errore nella registrazione. Email o username già esistenti?");
            request.getRequestDispatcher("WEB-INF/jsp/registrazione.jsp").forward(request, response);
        }
    }
}