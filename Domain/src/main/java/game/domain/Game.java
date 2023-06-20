package game.domain;

import jakarta.persistence.*;

import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "games")
@AttributeOverride(name = "id", column = @Column(name = "id"))

public class Game extends Entity<UUID> {
    @Column(name = "player_one_id")
    private UUID playerOneId;
    @Column(name = "player_two_id")
    private UUID playerTwoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_one_coord")
    private Coordinates playerOnePlaneLocation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_two_coord")
    private Coordinates playerTwoPlaneLocation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private User winner;

    public Game() {
        setId(UUID.randomUUID());
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(UUID playerOneId) {
        this.playerOneId = playerOneId;
    }

    public UUID getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(UUID playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public Coordinates getPlayerOnePlaneLocation() {
        return playerOnePlaneLocation;
    }

    public void setPlayerOnePlaneLocation(Coordinates playerOnePlaneLocation) {
        this.playerOnePlaneLocation = playerOnePlaneLocation;
    }

    public Coordinates getPlayerTwoPlaneLocation() {
        return playerTwoPlaneLocation;
    }

    public void setPlayerTwoPlaneLocation(Coordinates playerTwoPlaneLocation) {
        this.playerTwoPlaneLocation = playerTwoPlaneLocation;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }
}
