package game.services;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.SessionData;
import game.domain.User;

import java.util.UUID;

public interface Services {
    String faCeva(String string, UUID sid);

    SessionData attemptLogin(User user);

    void logout(UUID sid);

    Game startGame(Coordinates coordinates, UUID sid);

    boolean hasValidSession(UUID sid);

    void makeMove(UUID gameId, Coordinates coordinates, UUID sid);

    boolean hasWon(Game game, User user, Coordinates move);
}
