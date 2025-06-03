package dao.jpa;

import dao.model.CategoriaDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Categoria;

import java.util.List;

public class JpaCategoriaDao implements CategoriaDao {

    private EntityManager em;

    public JpaCategoriaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Categoria> getAllCategorie() {
        TypedQuery<Categoria> query = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
        return query.getResultList();
    }

    @Override
    public List<Categoria> findAll() {
        return getAllCategorie();
    }

    @Override
    public Categoria findById(Long id) {
        return em.find(Categoria.class, id);
    }

}