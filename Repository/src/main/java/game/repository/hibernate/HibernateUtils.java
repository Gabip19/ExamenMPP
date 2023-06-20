package game.repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = initialize();
        }
        return sessionFactory;
    }

    private static SessionFactory initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            return sessionFactory;
        }
        catch (Exception e) {
            System.err.println("[HIBERNATE] Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return null;
    }

    public static void closeSession() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
            System.out.println("[HIBERNATE] Session factory closed.");
        }
    }
}
