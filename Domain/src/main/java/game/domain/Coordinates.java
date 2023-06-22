package game.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "coordinates")

public class Coordinates extends Entity<UUID> {

    @Column(name = "position")
    private int position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "value")
    private int value;

    @Column(name = "owned")
    private boolean owned;

    public Coordinates() {
        setId(UUID.randomUUID());
        this.owned = false;
    }

    public Coordinates(int position, int value) {
        this.position = position;
        this.value = value;
        this.owned = false;
        setId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return position == that.position && Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, game);
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
