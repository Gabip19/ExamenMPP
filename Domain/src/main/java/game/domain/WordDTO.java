package game.domain;

public class WordDTO {
    private String word;
    private int firstPosition;
    private int secondPosition;

    public WordDTO(Word coordinates) {
        firstPosition = getFirstPosition();
        secondPosition = getSecondPosition();
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
