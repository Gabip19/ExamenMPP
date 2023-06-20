package game.services;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.User;

import java.util.UUID;

public interface Services {
    String faCeva(String string, UUID sid);
    User attemptLogin(User user);
    void logout(UUID sid);
    Game startGame(Coordinates coordinates, UUID sid);
}
