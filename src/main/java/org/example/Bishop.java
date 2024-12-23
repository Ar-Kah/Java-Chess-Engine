package org.example;

public class Bishop extends ChessPiece{
    public Bishop(String color, int[] position) {
        super("B", color, position);
    }

    @Override
    public void move(Board board, int[] moveTo) {
        int[][] possibleMoves = {
                {1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7},
                {-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7},
                {-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7},
                {1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}
        };

    }
}
