package org.example;

public class Knight extends ChessPiece{
    public Knight(String color, int[] position) {
        super("N", color, position);
        super.name = "Knight";
    }

    @Override
    public boolean checkValidMove(ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
