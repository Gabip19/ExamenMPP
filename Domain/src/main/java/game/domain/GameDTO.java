package game.domain;

import java.io.Serializable;
import java.util.UUID;

public class GameDTO implements Serializable {
    private UUID gameId;
    private UUID activePlayerId;
    private UUID winnerId;

    public GameDTO(UUID gameId, UUID activePlayerId, UUID winnerId) {
        this.gameId = gameId;
        this.activePlayerId = activePlayerId;
        this.winnerId = winnerId;
    }

    public GameDTO(Game game) {
        this.gameId = game.getId();
        this.activePlayerId = game.getActivePlayerId();
        this.winnerId = game.getWinner().getId();
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
