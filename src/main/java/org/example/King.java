package org.example;

public class King extends ChessPiece{
    public King(String color, int[] position) {
        super("K", color, position);
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        return false;
    }
}
