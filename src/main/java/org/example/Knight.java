package org.example;

public class Knight extends ChessPiece{
    public Knight(String color, int[] position) {
        super("N", color, position);
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        return false;
    }
}
