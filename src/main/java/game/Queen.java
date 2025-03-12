package game;

public class Queen extends Piece{
    public Queen(int color, int index) {
        super(color, index);
    }

    @Override
    public boolean canMove(int targetIndex) {
        return false;
    }
}
