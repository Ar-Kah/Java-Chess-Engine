package org.example;

public class Space extends ChessPiece {
    public Space(int[] position) {
        super("-", "N", position);
    }

    @Override
    public void move(Board board, int[] moveTo) {
    }
}
