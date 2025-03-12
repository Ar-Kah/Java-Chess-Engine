package game;

public abstract class Piece {

    public int color;
    public int index;
    public Piece(int color, int index) {
        this.color =color;
        this.index = index;
    }

    public abstract boolean canMove(int targetIndex);
    public boolean isWithinBoard(int targetIndex) {
        return targetIndex >= 0 && targetIndex <= 63;
    }
}
