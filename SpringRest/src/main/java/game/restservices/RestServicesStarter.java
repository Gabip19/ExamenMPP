package game.restservices;

import game.domain.User;
import game.repository.UserRepository;
import game.repository.hibernate.HibernateUtils;
import game.repository.hibernate.UserDBRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Properties;

@ComponentScan({"game.restservices", "game.repository"})
@SpringBootApplication
public class RestServicesStarter {

    public static void main(String[] args) {
//        HibernateUtils.getSessionFactory().openSession();
//        UserRepository userRepository = new UserDBRepository();
//        userRepository.add(new User("User1"));
//        userRepository.add(new User("User2"));
//        userRepository.add(new User("User3"));
        SpringApplication.run(RestServicesStarter.class, args);
        HibernateUtils.closeSession();
    }

    @Bean(name="properties")
    public static Properties readProperties() {
        Properties properties = new Properties();
        try {
            properties.load(RestServicesStarter.class.getResourceAsStream("/server.config"));
            System.out.println("Server properties set.");
            properties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.config " + e);
            return null;
        }
        return properties;
    }
}