package game;

import java.util.List;

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
    public List<int[]> getMoves(Board board) {
        return null;
    }

}
