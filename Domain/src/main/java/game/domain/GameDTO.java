package game.domain;

import java.io.Serializable;
import java.util.UUID;

public class GameDTO implements Serializable {
    private UUID gameId;
    private UUID activePlayerId;
    private UUID winnerId;

    public GameDTO(UUID gameId, UUID activePlayerId, User winner) {
        this.gameId = gameId;
        this.activePlayerId = activePlayerId;
        UUID winnerId = null;
        if (winner != null) winnerId = winner.getId();
        this.winnerId = winnerId;
    }

    public GameDTO(Game game) {
//        this.gameId = game.getId();
//        this.activePlayerId = game.getActivePlayerId();
//        UUID winnerId = null;
//        if (game.getWinner() != null) winnerId = game.getWinner().getId();
//        this.winnerId = winnerId;
    }

    public GameDTO() {
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getActivePlayerId() {
        return activePlayerId;
    }

    public void setActivePlayerId(UUID activePlayerId) {
        this.activePlayerId = activePlayerId;
    }

    public UUID getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(UUID winnerId) {
        this.winnerId = winnerId;
    }
}
