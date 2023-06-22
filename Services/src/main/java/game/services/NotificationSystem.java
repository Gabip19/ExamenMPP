package game.services;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.User;

import java.util.List;

public interface NotificationSystem {
    void notifyGameStarted(User user, List<Game> gameList);
    void notifyTopUpdate(List<Game> gameList);
    void notifyNewMove(Game game, Coordinates coordinates);
}
