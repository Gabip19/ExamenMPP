package game.repository.hibernate;

import game.domain.Game;
import game.domain.GameStatus;
import game.domain.User;
import game.repository.GameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GameDBRepository implements GameRepository {
    private static final Logger logger = LogManager.getLogger();

    public GameDBRepository() {
        logger.info("Initializing GameRepository.");
    }

    @Override
    public void add(Game elem) {
        logger.traceEntry("Saving Game {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[GAME REPO] Add game failed: " + e.getMessage());
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
    public void update(Game elem, UUID id) {
        logger.traceEntry("Updating Game {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Game game = session.get(Game.class, id);
//                game.setTotalSum(elem.getTotalSum());
//                game.setCurrentPosition(elem.getCurrentPosition());
//                game.setGameStatus(elem.getGameStatus());
//                game.setGenerationNumber(elem.getGenerationNumber());
                session.merge(game);
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[GAME REPO] Update game failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
    }

    @Override
    public Game findById(UUID id) {
        logger.traceEntry("Finding Game by id {}", id);
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
                logger.traceExit(game);
                return game;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[GAME REPO] Find game by id failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit(null);
        return null;
    }

    @Override
    public Iterable<Game> findAll() {
        return null;
    }

    @Override
    public List<Game> findByGameStatus(GameStatus gameStatus) {
        logger.traceEntry("Finding Games by status {}", gameStatus);
        List<Game> games;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                games = session.createQuery("from Game g where g.gameStatus = ?1", Game.class)
                        .setParameter(1, gameStatus)
                        .list();
                transaction.commit();
                logger.traceExit(games);
                return games;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[GAME REPO] Find all games by status failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        logger.traceExit();
        return new ArrayList<>();
    }

    @Override
    public List<Game> findByUserAndStatus(User user, GameStatus gameStatus) {
        logger.traceEntry("Finding Games by user {} and status {}", user, gameStatus);
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
                logger.traceExit(games);
                return games;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[GAME REPO] Find all games by user and status failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        logger.traceExit();
        return new ArrayList<>();
    }

    @Override
    public Game findByIdAndStatus(UUID gameId, GameStatus gameStatus) {
        logger.traceEntry("Finding Games by id {} and status {}", gameId, gameStatus);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Game game = session.createQuery("from Game g where g.gameStatus = ?1 and g.id = ?2", Game.class)
                        .setParameter(1, gameStatus)
                        .setParameter(2, gameId)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                logger.traceExit(game);
                return game;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[GAME REPO] Find all games by user and status failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        logger.traceExit();
        return null;
    }
}
