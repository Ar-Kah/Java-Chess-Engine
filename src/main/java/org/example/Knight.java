package org.example;

public class Knight extends ChessPiece{
    public Knight(String color, int[] position) {
        super("N", color, position);
    }

    @Override
    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
