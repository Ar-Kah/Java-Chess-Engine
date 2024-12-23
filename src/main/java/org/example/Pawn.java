package org.example;

import java.util.Arrays;

public class Pawn extends ChessPiece {
    public Pawn(String color, int[] position) {
        super("P", color, position);
    }

    @Override
    public void move(Board board, int[] moveTo) {
        int row = moveTo[0];
        int column = moveTo[1];
        updateBoard(board, row, column);
    }

    public void updateBoard(Board board, int row, int column) {
        // move space to pawns last position
        board.board[this.position[0]][this.position[1]] = new Space(this.position);
        // move piece to new location
        this.position[0] = row;
        this.position[1] = column;
        board.board[row][column] = this;
        board.printBoard();
    }
}
