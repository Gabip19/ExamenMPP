package game.restservices.services;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.User;
import game.domain.validator.exceptions.LoginException;
import game.repository.GameRepository;
import game.repository.UserRepository;
import game.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Component
public class ConcreteService implements Services {
    private final UserRepository userRepo;
    private final GameRepository gameRepo;
    private final HashMap<UUID, User> activeSessions = new HashMap<>();
    private final Queue<Game> openGames = new LinkedList<>();

    @Autowired
    public ConcreteService(UserRepository userRepo, GameRepository gameRepo) {
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
    }

    @Override
    public String faCeva(String string, UUID sid) {
        if (activeSessions.containsKey(sid)) {
            return string.toUpperCase();
        }
        return "Nu e cookie :(";
    }

    @Override
    public User attemptLogin(User user) {
        if (activeSessions.containsValue(user)) {
            throw new LoginException("User already logged in.");
        }

        User existingUser = userRepo.findByName(user.getName());
        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            UUID sessionId = UUID.randomUUID();
            activeSessions.put(sessionId, existingUser);
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

    @Override
    public Game startGame(Coordinates coordinates, UUID sid) {
        coordinates.setId(coordinates.getX() * 10 + coordinates.getY());
        Game currentGame = new Game();
        if (openGames.isEmpty()) {
            currentGame.setPlayerOneId(activeSessions.get(sid).getId());
            currentGame.setPlayerOnePlaneLocation(coordinates);
        } else {
            currentGame = openGames.poll();
            currentGame.setPlayerTwoId(activeSessions.get(sid).getId());
            currentGame.setPlayerTwoPlaneLocation(coordinates);
            gameRepo.add(currentGame);
        }
        return currentGame;
    }
}
