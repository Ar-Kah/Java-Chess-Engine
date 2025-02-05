package game;

import java.util.List;

public class Space extends ChessPiece {
    public int value = 0;
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

    @Override
    public ChessPiece clone() {
        return new Space(this.position.clone());
    }

    @Override
    public int getValue() {
        return value;
    }
}
