package game;

public class Pawn extends Piece {
    public Pawn(int color, int index) {
        super(color, index);
    }

    @Override
    public boolean canMove(int targetIndex) {
        return false;
    }
}
