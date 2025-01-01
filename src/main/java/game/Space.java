package game;

public class Space extends ChessPiece {

    public Space(int[] position) {
        super("-", "N", position);
        super.name = "Space";
    }

    @Override
    public boolean canMoveTo(ChessPiece pieceToReplace, Board board) {
        return false; // this method does not do anything because you can't move an empty space
    }

    @Override
    public boolean isCheckingKing(Board board) {
        return false; // can not ever check king
    }
}
