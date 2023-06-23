package game.domain;

public class WordDTO {
    private String word;
    private int firstPosition;
    private int secondPosition;

    public WordDTO(Word word) {
        this.word = word.getWord().getWord();
        firstPosition = word.getFirstPosition();
        secondPosition = word.getSecondPosition();
    }

    public WordDTO() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
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
}
