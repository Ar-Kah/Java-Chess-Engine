package org.example;

public class Space extends ChessPiece {
    public Space(int[] position) {
        super("-", "N", position);
    }

    @Override
    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate) {
        return false;
    }
}
