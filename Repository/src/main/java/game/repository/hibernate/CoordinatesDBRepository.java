package game.repository.hibernate;

import game.domain.Coordinates;
import game.repository.CoordinatesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CoordinatesDBRepository implements CoordinatesRepository {
    private static final Logger logger = LogManager.getLogger();

    public CoordinatesDBRepository() {
        logger.info("Initializing CoordinatesRepository.");
    }

    @Override
    public void add(Coordinates elem) {
        logger.traceEntry("Saving Coordinates {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.merge(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[Coordinates REPO] Add coordinate failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void update(Coordinates elem, UUID id) {
        logger.traceEntry("Updating Coordinates {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Coordinates coordinates = session.get(Coordinates.class, id);
//                coordinates.setOwned(elem.isOwned());
                session.merge(coordinates);
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[COORDINATES REPO] Update coordinate failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
    }

    @Override
    public Coordinates findById(UUID id) {
        return null;
    }

    @Override
    public Iterable<Coordinates> findAll() {
        return null;
    }
}
