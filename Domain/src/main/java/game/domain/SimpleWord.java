package game.domain;

import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "word")

public class SimpleWord extends Entity<Integer> {
    private String word;

    public SimpleWord(String word) {
        this.word = word;
    }

    public SimpleWord() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
