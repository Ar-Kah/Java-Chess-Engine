package org.example;

public class Queen extends ChessPiece{
    public Queen(String color, int[] position) {
        super("Q", color, position);
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        return false;
    }
}
