package game.repository.hibernate;

import game.domain.Game;
import game.repository.GameRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameDBRepository implements GameRepository {
    public GameDBRepository() {
    }

    @Override
    public void add(Game elem) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("[ORDER REPO] Add game failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void update(Game elem, UUID id) {

    }

    @Override
    public Game findById(UUID id) {
        return null;
    }

    @Override
    public Iterable<Game> findAll() {
        return null;
    }
}
