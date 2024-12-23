package org.example;

public class Queen extends ChessPiece{
    public Queen(String color, int[] position) {
        super("Q", color, position);
    }

    @Override
    public void move(Board board, int[] moveTo) {
    }
}
