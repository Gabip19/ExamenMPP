package game.repository.hibernate;

import game.domain.SimpleWord;
import game.domain.User;
import game.repository.SimpleWordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class SimpleWordDBRepository implements SimpleWordRepository {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(SimpleWord elem) {
        logger.traceEntry("Saving Coordinates {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.merge(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[SIMPLE_WORD REPO] Add coordinate failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(SimpleWord elem, Integer id) {

    }

    @Override
    public SimpleWord findById(Integer id) {
        logger.traceEntry("Finding User by id {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String queryString = "from SimpleWord sp where sp.id = ?1";
                SimpleWord user = session.createQuery(queryString, SimpleWord.class)
                        .setParameter(1, id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                logger.traceExit(user);
                return user;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[SIMPLE_WORD REPO] Find user by id failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<SimpleWord> findAll() {
        return null;
    }

    @Override
    public long getWordsNumber() {
        logger.traceEntry("Finding all Users");

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                long num = session.createQuery("from SimpleWord sp", SimpleWord.class).stream().count();
                transaction.commit();
                logger.traceExit();
                return num;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[USER REPO] Find all users failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        logger.traceExit();
        return 0;
    }
}
