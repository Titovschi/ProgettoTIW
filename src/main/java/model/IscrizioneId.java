package model;

import java.io.Serializable;
import java.util.Objects;

public class IscrizioneId implements Serializable {

    private Long utente;
    private Long evento;

    public IscrizioneId() {}

    public IscrizioneId(Long utente, Long evento) {
        this.utente = utente;
        this.evento = evento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IscrizioneId)) return false;
        IscrizioneId that = (IscrizioneId) o;
        return Objects.equals(utente, that.utente) &&
                Objects.equals(evento, that.evento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utente, evento);
    }
}