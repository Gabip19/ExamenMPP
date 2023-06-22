package game.services;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.SessionData;
import game.domain.User;

import java.util.List;
import java.util.UUID;

public interface Services {
    String faCeva(String string, UUID sid);

    SessionData attemptLogin(User user);

    void logout(UUID sid);

    Game startGame(UUID sid);

    boolean hasValidSession(UUID sid);

    Game makeMove(UUID gameId, Coordinates coordinates, UUID sid);

    List<Game> getAllGames();

    List<Game> getAllFinishedGamesForUser(UUID userId);

//    boolean hasWon(Game game, User user, Coordinates move);
}
