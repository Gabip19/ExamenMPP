package game.repository.hibernate;

import game.domain.Coordinates;
import game.repository.CoordinatesRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesDBRepository implements CoordinatesRepository {
    @Override
    public void add(Coordinates elem) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.merge(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("[Coordinates REPO] Add coordinate failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Coordinates elem, Integer id) {

    }

    @Override
    public Coordinates findById(Integer id) {
        return null;
    }

    @Override
    public Iterable<Coordinates> findAll() {
        return null;
    }
}
