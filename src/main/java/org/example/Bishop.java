package org.example;

public class Bishop extends ChessPiece{
    public Bishop(String color, int[] position) {
        super("B", color, position);
    }

    @Override
    public boolean checkValidMove(ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
