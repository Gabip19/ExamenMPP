package game.services;

import game.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface Services {
    String faCeva(String string, UUID sid);

    SessionData attemptLogin(User user);

    void logout(UUID sid);

    Game startGame(UUID sid);

    boolean hasValidSession(UUID sid);

    Game makeMove(UUID gameId, PlayerMove coordinates, UUID sid);

    List<Game> getAllGames();

    List<Game> getAllFinishedGamesForUser(UUID userId);

    Game getEndedGameWithId(UUID gameId);

    void replaceConfiguration(String gameId, ArrayList<WordDTO> configuration);

//    boolean hasWon(Game game, User user, Coordinates move);
}
