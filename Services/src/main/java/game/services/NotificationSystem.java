package game.services;

import game.domain.Game;

public interface NotificationSystem {
    void notifyGameEnded(Game game);
    void notifySwitchTurns(Game game);
}
