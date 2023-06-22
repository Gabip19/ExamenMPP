package game.domain;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "coordinates")

public class Coordinates extends Entity<UUID> {

    private int x;
    private int y;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
        setId(UUID.randomUUID());
    }

    public Coordinates() {
        setId(UUID.randomUUID());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y && Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, game);
    }
}
