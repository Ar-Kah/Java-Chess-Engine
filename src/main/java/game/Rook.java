package game;

public class Rook extends Piece{
    public Rook(int color, int index) {
        super(color, index);
    }

    @Override
    public boolean canMove(int targetIndex) {
        return false;
    }
}
