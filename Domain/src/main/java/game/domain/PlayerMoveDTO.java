package game.domain;

public class PlayerMoveDTO {
    private int position1;
    private int position2;

    public PlayerMoveDTO() {
    }

    public PlayerMoveDTO(int position1, int position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    public PlayerMoveDTO(PlayerMove playerMove) {
        this.position1 = playerMove.getPosition1();
        this.position2 = playerMove.getPosition2();
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
