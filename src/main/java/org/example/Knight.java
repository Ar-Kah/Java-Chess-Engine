package org.example;

public class Knight extends ChessPiece{
    public Knight(String color, int[] position) {
        super("N", color, position);
    }

    @Override
    public void move(Board board, int[] moveTo) {
    }
}
