package dao.model;

import model.Categoria;
import java.util.List;

public interface CategoriaDao {
    List<Categoria> getAllCategorie();
    public List<Categoria> findAll();
    public Categoria findById(Long id);
}