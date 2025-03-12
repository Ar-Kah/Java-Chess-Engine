package game;

public class King extends Piece{
    public King(int color, int index) {
        super(color, index);
    }

    @Override
    public boolean canMove(int targetIndex) {
        return false;
    }
}
