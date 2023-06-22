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

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ConcreteService implements Services {
    private final UserRepository userRepo;
    private final GameRepository gameRepo;
    private final CoordinatesRepository coordinatesRepo;
    private final Map<UUID, User> activeSessions = new HashMap<>();
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
        if (existingUser != null) {
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
    public Game startGame(UUID sid) {
        User user = activeSessions.get(sid);
        Game currentGame = new Game();
        currentGame.setScore(0);
        currentGame.setStartDate(LocalDateTime.now());
        currentGame.setPlayer(user);

        Random rand = new Random();
        for (int i = 1; i <= 4; i++) {
            int mineY = 1 + rand.nextInt(4);

            Coordinates mine = new Coordinates(i, mineY);
            currentGame.addMineCoordinate(mine);
        }

        int mineX = 1 + rand.nextInt(4);
        int mineY = 1 + rand.nextInt(4);
        Coordinates mine = new Coordinates(mineX, mineY);
        currentGame.addMineCoordinate(mine);

        gameRepo.add(currentGame);

        return currentGame;
    }

    @Override
    public boolean hasValidSession(UUID sid) {
        return activeSessions.containsKey(sid);
    }

    @Override
    public void makeMove(UUID gameId, Coordinates coordinates, UUID sid) {
//        User user = activeSessions.get(sid);
//        Game game = activeGames.get(gameId);
//        if (user.getId().equals(game.getActivePlayerId())) {
//            if (hasWon(game, user, coordinates)) {
//                game.setWinner(user);
//                gameRepo.update(game, gameId);
//                activeGames.remove(gameId);
//                notificationSystem.notifyGameEnded(game);
//            } else {
//                game.switchActivePlayer();
//                notificationSystem.notifyNewMove(game, coordinates);
//            }
//        }
    }

    public boolean hasWon(Game game, User user, Coordinates move) {
//        return game.getPlayerTwoId().equals(user.getId()) && game.getPlayerOnePlaneLocation().equals(move) ||
//                game.getPlayerOneId().equals(user.getId()) && game.getPlayerTwoPlaneLocation().equals(move);
        return false;
    }
}
