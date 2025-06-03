package servlet;

import dao.DaoFactory;
import dao.model.IscrizioneDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Evento;
import model.Utente;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/iscriviti")
public class IscrizioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IscrizioneDao iscrizioneDao;

    @Override
    public void init() throws ServletException {
        iscrizioneDao = DaoFactory.getDaoFactory().getIscrizioneDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Utente utente = (Utente) session.getAttribute("user");
        String eventoIdStr = request.getParameter("eventoId");
        String redirectUrl = request.getContextPath() + "/homeutente";

        try {
            if (eventoIdStr != null && !eventoIdStr.trim().isEmpty()) {
                Long eventoId = Long.parseLong(eventoIdStr);
                Evento evento = DaoFactory.getDaoFactory().getEventoDao().findById(eventoId);

                if (evento != null) {
                    // Controlla capacità massima
                    long iscritti = iscrizioneDao.countIscrittiPerEvento(evento);
                    if (iscritti >= evento.getCapacita()) {
                        String errorMsg = URLEncoder.encode("Capacità massima raggiunta per questo evento.", "UTF-8");
                        redirectUrl += "?error=" + errorMsg;
                        response.sendRedirect(redirectUrl);
                        return;
                    }
                    // Controlla se l'utente è già iscritto
                    if (!iscrizioneDao.isGiaIscritto(evento, utente)) {
                        boolean success = iscrizioneDao.createIscrizione(evento, utente);
                        if (success) {
                            redirectUrl += "?success=true";
                        } else {
                            System.err.println("Errore durante la creazione dell'iscrizione per utente " + utente.getUsername() + " all'evento " + evento.getNome());
                            String errorMsg = URLEncoder.encode("Errore durante l'iscrizione.", "UTF-8");
                            redirectUrl += "?error=" + errorMsg;
                        }
                    } else {
                        String errorMsg = URLEncoder.encode("Sei già iscritto a questo evento.", "UTF-8");
                        redirectUrl += "?error=" + errorMsg;
                    }
                } else {
                    String errorMsg = URLEncoder.encode("Evento non trovato.", "UTF-8");
                    redirectUrl += "?error=" + errorMsg;
                }
            } else {
                String errorMsg = URLEncoder.encode("ID evento non valido.", "UTF-8");
                redirectUrl += "?error=" + errorMsg;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String errorMsg = URLEncoder.encode("ID evento non valido.", "UTF-8");
            redirectUrl += "?error=" + errorMsg;
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = URLEncoder.encode("Errore: " + e.getMessage(), "UTF-8");
            redirectUrl += "?error=" + errorMsg;
        }

        // Reindirizza a homeutente per aggiornare la pagina
        response.sendRedirect(redirectUrl);
    }
}