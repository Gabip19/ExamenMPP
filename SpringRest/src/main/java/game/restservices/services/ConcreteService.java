package game.restservices.services;

import game.domain.User;
import game.domain.validator.exceptions.LoginException;
import game.repository.UserRepository;
import game.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class ConcreteService implements Services {
    private final UserRepository userRepo;
    private final HashMap<UUID, User> activeSessions = new HashMap<>();

    @Autowired
    public ConcreteService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String faCeva(String string, UUID sid) {
        if (activeSessions.containsKey(sid)) {
            return string.toUpperCase();
        }
        return "Nu e cookie :(";
    }

    @Override
    public UUID attemptLogin(User user) {
        if (activeSessions.containsValue(user)) {
            throw new LoginException("User already logged in.");
        }

        User existingUser = userRepo.findByName(user.getName());
        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            UUID sessionId = UUID.randomUUID();
            activeSessions.put(sessionId, user);
            return sessionId;
        }
        throw new LoginException("Invalid Credentials.");
    }

    @Override
    public void logout(UUID sid) {
        if (activeSessions.containsKey(sid)) {
            activeSessions.remove(sid);
        } else {
            throw new LoginException("Not logged in.");
        }
    }
}
