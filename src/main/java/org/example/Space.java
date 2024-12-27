package org.example;

public class Space extends ChessPiece {
    public Space(int[] position) {
        super("-", "N", position);
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        return false;
    }
}
