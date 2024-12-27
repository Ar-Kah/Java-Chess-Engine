package org.example;

public class Bishop extends ChessPiece{
    public Bishop(String color, int[] position) {
        super("B", color, position);
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        return false;
    }
}
