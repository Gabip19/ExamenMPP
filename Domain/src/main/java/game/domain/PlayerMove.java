package game.domain;

import jakarta.persistence.*;

import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "player_moves")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class PlayerMove extends Entity<UUID> {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;
    private int position1;
    private int position2;

    public PlayerMove() {
        setId(UUID.randomUUID());
    }

    public PlayerMove(Game game, int position1, int position2) {
        this.game = game;
        this.position1 = position1;
        this.position2 = position2;
        setId(UUID.randomUUID());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPosition1() {
        return position1;
    }

    public void setPosition1(int position1) {
        this.position1 = position1;
    }

    public int getPosition2() {
        return position2;
    }

    public void setPosition2(int position2) {
        this.position2 = position2;
    }
}
