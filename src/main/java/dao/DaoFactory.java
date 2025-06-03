package dao;

import dao.model.EventoDao;
import dao.model.IscrizioneDao;
import dao.model.UtenteDao;

public abstract class DaoFactory {
    public abstract UtenteDao getUtenteDao();
    public abstract EventoDao getEventoDao();
    public abstract IscrizioneDao getIscrizioneDao();
    public abstract dao.model.CategoriaDao getCategoriaDao();

    public static DaoFactory getDaoFactory() {
        return new dao.jpa.JpaDaoFactory();
    }
}