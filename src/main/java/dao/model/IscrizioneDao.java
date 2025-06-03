package dao.model;

import model.Evento;
import model.Utente;
import java.util.List;

public interface IscrizioneDao {
    boolean createIscrizione(Evento evento, Utente utente);
    List<Evento> getEventiIscritti(Utente utente);
    boolean isGiaIscritto(Evento evento, Utente utente);
    boolean deleteIscrizione(Evento evento, Utente utente);
    long countIscrittiPerEvento(Evento evento);
    public List<Utente> getPartecipantiEvento(Long eventoId);
    void rimuoviIscrizioniPerEvento(Long eventoId);

}