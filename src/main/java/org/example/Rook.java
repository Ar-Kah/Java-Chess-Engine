package org.example;

public class Rook extends ChessPiece {
    public Rook(String color, int[] position) {
        super("R", color, position);
    }

    @Override
    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
