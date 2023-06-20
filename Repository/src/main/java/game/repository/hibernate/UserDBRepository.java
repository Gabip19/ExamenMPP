package game.repository.hibernate;

import game.domain.User;
import game.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserDBRepository implements UserRepository {
    public UserDBRepository() {}

    @Override
    public void add(User elem) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(elem);
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("[ORDER REPO] Add user failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

//    @Override
//    public List<User> findByAgent(User agent) {
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
//                return orders;
//            } catch (RuntimeException e) {
//                System.err.println("[ORDER REPO] Find all orders by agent failed: " + e.getMessage());
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
//
//        return new ArrayList<>();
//    }

    @Override
    public void delete(UUID id) {
        throw new RuntimeException("Not implemented.\n");
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Transaction transaction = null;
//            try {
//                transaction = session.beginTransaction();
//                Order order = session.get(Order.class, id);
//                session.remove(order);
//                transaction.commit();
//            } catch (RuntimeException e) {
//                System.err.println("[ORDER REPO] Update order failed: " + e.getMessage());
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
    }

    @Override
    public void update(User elem, UUID id) {
        throw new RuntimeException("Not implemented.\n");
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
//                System.err.println("[ORDER REPO] Update order failed: " + e.getMessage());
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//            }
//        }
    }

    @Override
    public User findById(UUID uuid) {
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
                return user;
            } catch (RuntimeException e) {
                System.err.println("[USER REPO] Find user by id failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                users = session.createQuery("from User u", User.class).list();
                transaction.commit();
            } catch (RuntimeException e) {
                System.err.println("[PRODUCT REPO] Find all users failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

        return users;
    }

    @Override
    public User findByName(String name) {
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
                return user;
            } catch (RuntimeException e) {
                System.err.println("[USER REPO] Find user by name failed: " + e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }
}
