package org.example;

public class Rook extends ChessPiece {
    public Rook(String color, int[] position) {
        super("R", color, position);
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        return false;
    }
}
