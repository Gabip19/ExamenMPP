package game.restservices.services;

import game.domain.*;
import game.domain.validator.exceptions.LoginException;
import game.repository.SimpleWordRepository;
import game.repository.WordRepository;
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
    private final WordRepository wordRepo;
    private final SimpleWordRepository simpleWordRepo;
    private final Map<UUID, User> activeSessions = new HashMap<>();
    private final NotificationSystem notificationSystem;

    @Autowired
    public ConcreteService(UserRepository userRepo, GameRepository gameRepo, WordRepository coordinatesRepo, SimpleWordRepository simpleWordRepo, NotificationSystem notificationSystem) {
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
        this.wordRepo = coordinatesRepo;
        this.simpleWordRepo = simpleWordRepo;
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
        currentGame.setGameStatus(GameStatus.PLAYING);

        long wordsNum = simpleWordRepo.getWordsNumber();
        Set<Integer> usedPositions = new HashSet<>();
        Set<Integer> usedWordsIds = new HashSet<>();

        Random rand = new Random();
        while (usedPositions.size() != 10) {
            int wordId = Math.toIntExact(rand.nextLong(wordsNum));
            if (!usedWordsIds.contains(wordId)) {
                usedWordsIds.add(wordId);
                int position1 = rand.nextInt(10);
                int position2 = rand.nextInt(10);
                while (position1 == position2 || usedPositions.contains(position1) || usedPositions.contains(position2)) {
                    position1 = rand.nextInt(10);
                    position2 = rand.nextInt(10);
                }
                usedPositions.add(position1);
                usedPositions.add(position2);

                SimpleWord simpleWord = simpleWordRepo.findById(wordId);
                Word word = new Word();
                word.setWord(simpleWord);
                word.setFirstPosition(position1);
                word.setSecondPosition(position2);

                currentGame.addWord(word);
            }
        }

        gameRepo.add(currentGame);

        return currentGame;
    }

    @Override
    public boolean hasValidSession(UUID sid) {
        return activeSessions.containsKey(sid);
    }

    @Override
    public Game makeMove(UUID gameId, PlayerMove move, UUID sid) {
        Game game = gameRepo.findById(gameId);

        if (game.getGameStatus().equals(GameStatus.PLAYING)) {
            game.addPlayerMove(move);

            Word firstWord = null;
            Word secondWord = null;
            for (var word : game.getConfiguration()) {
                if (firstWord == null && goodPosition(move, word)) {
                    firstWord = word;
                } else if (secondWord == null && goodPosition(move, word)) {
                    secondWord = word;
                }
            }

            assert firstWord != null;
            assert secondWord != null;
            if (firstWord.getWord().getWord().equals(secondWord.getWord().getWord())) {
                game.setScore(game.getScore() - 2);
            } else {
                game.setScore(game.getScore() + 3);
            }

            if (game.getPlayerMoves().size() == 10) {
                game.setGameStatus(GameStatus.ENDED);
                game.setEndDate(LocalDateTime.now());
            }

            gameRepo.update(game, gameId);
        }
        return game;
    }

    private boolean goodPosition(PlayerMove move, Word word) {
        return (move.getPosition1() == word.getFirstPosition())
                || (move.getPosition2() == word.getFirstPosition())
                || (move.getPosition1() == word.getSecondPosition())
                || (move.getPosition2() == word.getSecondPosition());
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> getAllFinishedGames = gameRepo.findByGameStatus(GameStatus.ENDED);
        getAllFinishedGames.sort((Game o1, Game o2) -> {
            if (o1.getScore() == o2.getScore()) {
                if (o1.getDuration() < o2.getDuration()) {
                    return -1;
                } else if (o1.getDuration() > o2.getDuration()) {
                    return 1;
                } return 0;
            } else if (o1.getScore() < o2.getScore()) {
                return -1;
            } return 1;
        });
        return getAllFinishedGames;
    }

    @Override
    public List<Game> getAllFinishedGamesForUser(UUID userId) {
        User user = userRepo.findById(userId);
        return gameRepo.findByUserAndStatus(user, GameStatus.ENDED);
    }

    @Override
    public Game getEndedGameWithId(UUID gameId) {
        return gameRepo.findByIdAndStatus(gameId, GameStatus.ENDED);
    }

    @Override
    public void replaceConfiguration(String gameId, ArrayList<WordDTO> configuration) {
        ArrayList<Word> words = new ArrayList<>();
    }

//    public boolean hasWon(Game game, User user, Coordinates move) {
////        return game.getPlayerTwoId().equals(user.getId()) && game.getPlayerOnePlaneLocation().equals(move) ||
////                game.getPlayerOneId().equals(user.getId()) && game.getPlayerTwoPlaneLocation().equals(move);
//        return false;
//    }
}
