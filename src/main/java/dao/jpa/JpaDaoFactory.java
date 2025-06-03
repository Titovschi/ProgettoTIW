package dao.jpa;

import dao.DaoFactory;
import dao.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaDaoFactory extends DaoFactory {
    // Change to match the actual name in persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "ProgettoEsame";
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            System.out.println("EntityManagerFactory successfully initialized");
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR initializing EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static EntityManager getManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized. Check your persistence.xml configuration.");
        }
        return emf.createEntityManager();
    }

    @Override
    public UtenteDao getUtenteDao() {
        return JpaUtenteDao.getInstance();
    }

    @Override
    public EventoDao getEventoDao() {
        return JpaEventoDao.getInstance();
    }

    @Override
    public IscrizioneDao getIscrizioneDao() {
        return JpaIscrizioneDao.getInstance();
    }

    @Override
    public CategoriaDao getCategoriaDao() {
        return new JpaCategoriaDao(getManager());
    }
}