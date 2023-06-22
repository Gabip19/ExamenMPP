package game.repository.hibernate;

import game.domain.User;
import game.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserDBRepository implements UserRepository {
    private static final Logger logger = LogManager.getLogger();

    public UserDBRepository() {
        logger.info("Initializing UserRepository.");
    }

    @Override
    public void add(User elem) {
        logger.traceEntry("Saving User {}", elem);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[USER REPO] Add user failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
    }

//    @Override
//    public List<User> findByAgent(User agent) {
//         logger.traceEntry("Finding Games by user {}", elem);
//        List<Order> orders;
//
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Transaction transaction = null;
//            try {
//                transaction = session.beginTransaction();
//                orders = session.createQuery("from Order o where o.agent.id = ?1", Order.class)
//                        .setParameter(1, agent.getId())
//                        .list();
//                transaction.commit();
    //              logger.traceExit(orders);
//                return orders;
//            } catch (RuntimeException e) {
//                logger.error(e);
//                System.err.println("[ORDER REPO] Find all orders by agent failed: " + e.getMessage());
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
//          logger.traceExit();
//        return new ArrayList<>();
//    }

    @Override
    public void delete(UUID id) {
        throw new RuntimeException("Not implemented.\n");
//          logger.traceEntry("Finding Games by user {}", elem);
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//
//            Transaction transaction = null;
//            try {
//                transaction = session.beginTransaction();
//                Order order = session.get(Order.class, id);
//                session.remove(order);
//                transaction.commit();
//            } catch (RuntimeException e) {
//                logger.error(e);
//                System.err.println("[ORDER REPO] Update order failed: " + e.getMessage());
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
//        logger.traceExit();
    }

    @Override
    public void update(User elem, UUID id) {
        throw new RuntimeException("Not implemented.\n");
//      logger.traceEntry("Finding Games by user {}", elem);
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Transaction transaction = null;
//            try {
//                transaction = session.beginTransaction();
//                Order order = session.get(Order.class, id);
//                order.setStatus(elem.getStatus());
//                order.setClientName(elem.getClientName());
//                session.merge(order);
//                transaction.commit();
//            } catch (RuntimeException e) {
//                  logger.error(e);
//                System.err.println("[ORDER REPO] Update order failed: " + e.getMessage());
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
//        logger.traceExit();
    }

    @Override
    public User findById(UUID uuid) {
        logger.traceEntry("Finding User by id {}", uuid);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String queryString = "from User u where u.id = ?1";
                User user = session.createQuery(queryString, User.class)
                        .setParameter(1, uuid)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                logger.traceExit(user);
                return user;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[USER REPO] Find user by id failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry("Finding all Users");
        List<User> users = new ArrayList<>();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                users = session.createQuery("from User u", User.class).list();
                transaction.commit();
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[USER REPO] Find all users failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        logger.traceExit(users);
        return users;
    }

    @Override
    public User findByName(String name) {
        logger.traceEntry("Finding Users by name {}", name);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String queryString = "from User u where u.name = ?1";
                User user = session.createQuery(queryString, User.class)
                        .setParameter(1, name)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                logger.traceExit(user);
                return user;
            } catch (RuntimeException e) {
                logger.error(e);
                System.err.println("[USER REPO] Find user by name failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        logger.traceExit();
        return null;
    }
}
