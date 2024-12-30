package org.example;

public class Queen extends ChessPiece{
    public Queen(String color, int[] position) {
        super("Q", color, position);
    }


    @Override
    public boolean checkValidMove(ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
