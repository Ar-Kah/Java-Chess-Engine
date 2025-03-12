package game;

public class Knight extends Piece{
    public Knight(int color, int index) {
        super(color, index);
    }

    @Override
    public boolean canMove(int targetIndex) {
        return false;
    }
}
