package game;

public class Bishop extends Piece{
    public Bishop(int color, int index) {
        super(color, index);
    }

    @Override
    public boolean canMove(int targetIndex) {
        return false;
    }
}
