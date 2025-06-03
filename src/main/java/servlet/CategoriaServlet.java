package servlet;

import dao.DaoFactory;
import dao.model.CategoriaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Categoria;

import java.io.IOException;
import java.util.List;

@WebServlet("/categorie")
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDao categoriaDao = DaoFactory.getDaoFactory().getCategoriaDao();
        List<Categoria> categorie = categoriaDao.getAllCategorie();

        request.setAttribute("categorie", categorie);
        request.getRequestDispatcher("/WEB-INF/jsp/categorie.jsp").forward(request, response);
    }
}