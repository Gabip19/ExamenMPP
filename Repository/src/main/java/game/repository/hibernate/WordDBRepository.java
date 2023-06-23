package game.repository.hibernate;

import game.domain.Word;
import game.repository.WordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WordDBRepository implements WordRepository {
    private static final Logger logger = LogManager.getLogger();

    public WordDBRepository() {
        logger.info("Initializing CoordinatesRepository.");
    }

    @Override
    public void add(Word elem) {
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
    public void update(Word elem, UUID id) {
        logger.traceEntry("Updating Coordinates {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Word coordinates = session.get(Word.class, id);
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
    public Word findById(UUID id) {
        return null;
    }

    @Override
    public Iterable<Word> findAll() {
        return null;
    }
}
