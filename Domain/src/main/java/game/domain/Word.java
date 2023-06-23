package game.domain;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "game_word")

public class Word extends Entity<UUID> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word")
    private SimpleWord word;
    private int firstPosition;
    private int secondPosition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    public Word(SimpleWord word, int firstPosition, int secondPosition, Game game) {
        this.word = word;
        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
        this.game = game;
        setId(UUID.randomUUID());
    }

    public Word() {
        setId(UUID.randomUUID());
    }

    public SimpleWord getWord() {
        return word;
    }

    public void setWord(SimpleWord word) {
        this.word = word;
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(int firstPosition) {
        this.firstPosition = firstPosition;
    }

    public int getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(int secondPosition) {
        this.secondPosition = secondPosition;
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
        Word word1 = (Word) o;
        return firstPosition == word1.firstPosition && secondPosition == word1.secondPosition && Objects.equals(word, word1.word) && Objects.equals(game, word1.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, firstPosition, secondPosition, game);
    }
}
