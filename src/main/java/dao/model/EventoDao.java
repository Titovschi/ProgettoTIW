package dao.model;

import model.Evento;
import model.Utente;
import java.util.List;

public interface EventoDao {
    boolean create(Evento evento);
    List<Evento> getEventiByOrganizzatore(Utente organizzatore);
    Evento findById(Long id);
    List<Evento> getEventiFuturi();

    List<Evento> getEventiByAdmin(Utente admin);
    List<Evento> getAllEventiFuturi();
    void update(Evento evento);
    void remove(Long id);

}