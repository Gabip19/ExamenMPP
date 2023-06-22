package game.domain;

public class CoordinatesDTO {
    private int position;
    private int value;
    private boolean owned;

    public CoordinatesDTO(Coordinates coordinates) {
        this.position = coordinates.getPosition();
        this.value = coordinates.getValue();
        this.owned = coordinates.isOwned();
    }

    public CoordinatesDTO() {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
