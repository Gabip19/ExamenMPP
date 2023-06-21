package game.restservices.services;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.SessionData;
import game.domain.User;
import game.domain.validator.exceptions.LoginException;
import game.repository.CoordinatesRepository;
import game.repository.GameRepository;
import game.repository.UserRepository;
import game.services.NotificationSystem;
import game.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConcreteService implements Services {
    private final UserRepository userRepo;
    private final GameRepository gameRepo;
    private final CoordinatesRepository coordinatesRepo;
    private final Map<UUID, User> activeSessions = new HashMap<>();
    private final Queue<Game> openGames = new LinkedList<>();
    private final Map<UUID, Game> activeGames = new HashMap<>();
    private final NotificationSystem notificationSystem;

    @Autowired
    public ConcreteService(UserRepository userRepo, GameRepository gameRepo, CoordinatesRepository coordinatesRepo, NotificationSystem notificationSystem) {
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
        this.coordinatesRepo = coordinatesRepo;
        this.notificationSystem = notificationSystem;
    }

    @Override
    public String faCeva(String string, UUID sid) {
        if (activeSessions.containsKey(sid)) {
            return string.toUpperCase();
        }
        return "Nu e cookie :(";
    }

    @Override
    public SessionData attemptLogin(User user) {
        if (activeSessions.containsValue(user)) {
            throw new LoginException("User already logged in.");
        }

        User existingUser = userRepo.findByName(user.getName());
        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            UUID sessionId = UUID.randomUUID();
            activeSessions.put(sessionId, existingUser);
            return new SessionData(sessionId, existingUser);
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
        coordinatesRepo.add(coordinates);
        Game currentGame;
        if (openGames.isEmpty()) {
            currentGame = new Game();
            currentGame.setPlayerOneId(activeSessions.get(sid).getId());
            currentGame.setPlayerOnePlaneLocation(coordinates);
            currentGame.setActivePlayerId(activeSessions.get(sid).getId());
            openGames.add(currentGame);
        } else {
            currentGame = openGames.poll();
            currentGame.setPlayerTwoId(activeSessions.get(sid).getId());
            currentGame.setPlayerTwoPlaneLocation(coordinates);
            activeGames.put(currentGame.getId(), currentGame);
            notificationSystem.notifyGameStarted(currentGame);
            gameRepo.add(currentGame);
        }
        return currentGame;
    }

    @Override
    public boolean hasValidSession(UUID sid) {
        return activeSessions.containsKey(sid);
    }

    @Override
    public void makeMove(UUID gameId, Coordinates coordinates, UUID sid) {
        User user = activeSessions.get(sid);
        Game game = activeGames.get(gameId);
        if (user.getId().equals(game.getActivePlayerId())) {
            if (hasWon(game, user, coordinates)) {
                game.setWinner(user);
                gameRepo.update(game, gameId);
                activeGames.remove(gameId);
                notificationSystem.notifyGameEnded(game);
            } else {
                game.switchActivePlayer();
                notificationSystem.notifyNewMove(game, coordinates);
            }
        }
    }

    public boolean hasWon(Game game, User user, Coordinates move) {
        return game.getPlayerTwoId().equals(user.getId()) && game.getPlayerOnePlaneLocation().equals(move) ||
                game.getPlayerOneId().equals(user.getId()) && game.getPlayerTwoPlaneLocation().equals(move);
    }
}
