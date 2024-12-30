package org.example;

public class Space extends ChessPiece {

    public Space(int[] position) {
        super("-", "N", position);
        super.name = "Space";
    }

    @Override
    public boolean checkValidMove(ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
