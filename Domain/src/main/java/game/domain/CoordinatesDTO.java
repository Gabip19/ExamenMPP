package game.domain;

public class CoordinatesDTO {
    private int x;
    private int y;

    public CoordinatesDTO(Coordinates coordinates) {
        x = coordinates.getX();
        y = coordinates.getY();
    }

    public CoordinatesDTO() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
