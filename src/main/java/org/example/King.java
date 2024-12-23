package org.example;

public class King extends ChessPiece{
    public King(String color, int[] position) {
        super("K", color, position);
    }

    @Override
    public void move(Board board, int[] moveTo) {
    }
}
