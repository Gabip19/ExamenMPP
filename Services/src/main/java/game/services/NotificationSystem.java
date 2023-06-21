package game.services;

import game.domain.Coordinates;
import game.domain.Game;

public interface NotificationSystem {
    void notifyGameStarted(Game game);
    void notifyGameEnded(Game game);
    void notifyNewMove(Game game, Coordinates coordinates);
}
