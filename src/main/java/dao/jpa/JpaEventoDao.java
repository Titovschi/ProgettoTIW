package dao.jpa;

import dao.model.EventoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Evento;
import model.Utente;
import java.time.LocalDate;
import java.util.List;

public class JpaEventoDao implements EventoDao {

    private static final JpaEventoDao instance = new JpaEventoDao();

    private JpaEventoDao() {
        // No longer storing an EntityManager here
    }

    public static JpaEventoDao getInstance() {
        return instance;
    }

    @Override
    public boolean create(Evento evento) {
        EntityManager em = JpaDaoFactory.getManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(evento);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Evento> getEventiByAdmin(Utente admin) {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            TypedQuery<Evento> query = em.createQuery(
                    "SELECT e FROM Evento e WHERE e.organizzatore = :admin", Evento.class);
            query.setParameter("admin", admin);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Evento> getEventiByOrganizzatore(Utente organizzatore) {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            TypedQuery<Evento> query = em.createQuery(
                    "SELECT e FROM Evento e WHERE e.organizzatore = :organizzatore", Evento.class);
            query.setParameter("organizzatore", organizzatore);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Evento> getEventiFuturi() {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            TypedQuery<Evento> query = em.createQuery(
                    "SELECT e FROM Evento e WHERE e.data >= :oggi ORDER BY e.data, e.ora", Evento.class);
            query.setParameter("oggi", LocalDate.now());
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Evento> getAllEventiFuturi() {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            LocalDate oggi = LocalDate.now();
            TypedQuery<Evento> query = em.createQuery(
                    "SELECT e FROM Evento e WHERE e.data >= :oggi ORDER BY e.data",
                    Evento.class);
            query.setParameter("oggi", oggi);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void insertTestEvent() {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            em.getTransaction().begin();

            // Recupera un organizzatore esistente
            TypedQuery<Utente> query = em.createQuery(
                    "SELECT u FROM Utente u WHERE u.ruolo = 'ORGANIZZATORE' ORDER BY u.id",
                    Utente.class);
            query.setMaxResults(1);
            Utente organizzatore = null;
            List<Utente> organizzatori = query.getResultList();
            if (!organizzatori.isEmpty()) {
                organizzatore = organizzatori.get(0);
            } else {
                // Se non c'è un organizzatore, usa l'ID 1 (presumibilmente admin)
                organizzatore = em.find(Utente.class, 1L);
            }

            // Crea un evento futuro di test
            Evento evento = new Evento();
            evento.setNome("Evento Test Futuro");
            evento.setDescrizione("Evento creato per test");
            evento.setData(LocalDate.now().plusDays(5)); // 5 giorni nel futuro
            evento.setOra(java.time.LocalTime.of(18, 0)); // 18:00
            evento.setLuogo("Test Location");
            evento.setOrganizzatore(organizzatore);

            em.persist(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Evento findById(Long id) {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void update(Evento evento) {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            em.getTransaction().begin();
            em.merge(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Long id) {
        EntityManager em = JpaDaoFactory.getManager();
        try {
            em.getTransaction().begin();
            Evento evento = em.find(Evento.class, id);
            if (evento != null) {
                em.remove(evento);
            }
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}