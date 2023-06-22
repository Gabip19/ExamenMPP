package game.restservices.services;

import game.domain.*;
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
        currentGame.setTotalSum(50);
        currentGame.setStartDate(LocalDateTime.now());
        currentGame.setPlayer(user);
        currentGame.setGameStatus(GameStatus.PLAYING);
        currentGame.setGenerationNumber(1);
        currentGame.setCurrentPosition(-1);

        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int value = 3 + rand.nextInt(23);

            Coordinates cell = new Coordinates(i, value);
            currentGame.addConfigurationCoordinate(cell);
        }

        gameRepo.add(currentGame);

        return currentGame;
    }

    @Override
    public boolean hasValidSession(UUID sid) {
        return activeSessions.containsKey(sid);
    }

    @Override
    public Game makeMove(UUID gameId, int generatedNumber, UUID sid) {
        Game game = gameRepo.findById(gameId);
        if (game.getGameStatus().equals(GameStatus.PLAYING) && game.getGenerationNumber() <= 3 && 1 <= generatedNumber && generatedNumber <= 3) {
            int nextPosition = game.getCurrentPosition() + generatedNumber;

            if (nextPosition >= 5) {
                nextPosition = nextPosition % 5;
                game.setTotalSum(game.getTotalSum() + 5);
            }

            final int nextPos = nextPosition;
            game.getConfiguration().stream()
                    .filter(coordinates -> coordinates.getPosition() == nextPos)
                    .forEach(coordinates -> {
                        if (!coordinates.isOwned()) {
                            game.setTotalSum(game.getTotalSum() - coordinates.getValue());
                            coordinates.setOwned(true);
                            coordinatesRepo.update(coordinates, coordinates.getId());
                        }
                    });

            game.setGenerationNumber(game.getGenerationNumber() + 1);
            game.setCurrentPosition(nextPos);

            if (game.getGenerationNumber() == 3) {
                game.setGameStatus(GameStatus.ENDED);
            }

            gameRepo.update(game, gameId);
        }
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> getAllFinishedGames = gameRepo.findByGameStatus(GameStatus.ENDED);
        getAllFinishedGames.sort((Game o1, Game o2) -> {
            if (o1.getTotalSum() > o2.getTotalSum()) {
                return -1;
            } else if (o1.getTotalSum() < o2.getTotalSum()) {
                return 1;
            }
            return 0;
        });
        return getAllFinishedGames;
    }

    @Override
    public List<Game> getAllFinishedGamesForUser(UUID userId) {
        User user = userRepo.findById(userId);
        return gameRepo.findByUserAndStatus(user, GameStatus.ENDED);
    }

//    public boolean hasWon(Game game, User user, Coordinates move) {
////        return game.getPlayerTwoId().equals(user.getId()) && game.getPlayerOnePlaneLocation().equals(move) ||
////                game.getPlayerOneId().equals(user.getId()) && game.getPlayerTwoPlaneLocation().equals(move);
//        return false;
//    }
}
