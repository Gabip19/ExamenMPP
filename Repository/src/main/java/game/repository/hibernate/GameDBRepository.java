package game.repository.hibernate;

import game.domain.Game;
import game.domain.GameStatus;
import game.domain.User;
import game.repository.GameRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Game game = session.get(Game.class, id);
                game.setScore(elem.getScore());
                game.setCurrentLevel(elem.getCurrentLevel());
                game.setEndDate(elem.getEndDate());
                game.setGameStatus(elem.getGameStatus());
                session.merge(game);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("[GAME REPO] Update game failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public Game findById(UUID id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String queryString = "from Game g where g.id = ?1";
                Game game = session.createQuery(queryString, Game.class)
                        .setParameter(1, id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                return game;
            } catch (RuntimeException e) {
                System.err.println("[GAME REPO] Find game by id failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<Game> findAll() {
        return null;
    }

    @Override
    public List<Game> findByGameStatus(GameStatus gameStatus) {
        List<Game> games;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                games = session.createQuery("from Game g where g.gameStatus = ?1", Game.class)
                        .setParameter(1, gameStatus)
                        .list();
                transaction.commit();
                return games;
            } catch (RuntimeException e) {
                System.err.println("[GAME REPO] Find all games by status failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        return new ArrayList<>();
    }

    @Override
    public List<Game> findByUserAndStatus(User user, GameStatus gameStatus) {
        List<Game> games;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                games = session.createQuery("from Game g where g.gameStatus = ?1 and g.player = ?2", Game.class)
                        .setParameter(1, gameStatus)
                        .setParameter(2, user)
                        .list();
                transaction.commit();
                return games;
            } catch (RuntimeException e) {
                System.err.println("[GAME REPO] Find all games by status failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        return new ArrayList<>();
    }
}
