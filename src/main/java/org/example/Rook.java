package org.example;

public class Rook extends ChessPiece {
    public Rook(String color, int[] position) {
        super("R", color, position);
    }

    @Override
    public boolean checkValidMove(ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
