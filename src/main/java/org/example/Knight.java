package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends ChessPiece{

    private final int[][] knightMoves = new int[][] {
            {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
    };

    public Knight(String color, int[] position) {
        super("N", color, position);
        super.name = "Knight";
    }

    @Override
    public boolean checkValidMove(ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {

        List<int[]> moves = new ArrayList<>();

        int row = this.position[0];
        int column = this.position[1];

        for (int[] move: knightMoves) {
            int newRow = row + move[0];
            int newColumn = column + move[1];

            // check bounds of board
            if (newRow > 7 | newColumn > 7 | newRow < 0 | newColumn < 0) {
                continue;
            }
            // can't capture same color piece
            ChessPiece piece = board.board[newRow][newColumn];
            if (piece.color.equals(this.color)) {
                continue;
            }
            moves.add(new int[]{newRow, newColumn});
        }

        for (int[] move: moves) {
            System.out.println(Arrays.toString(move));
            if (move[0] == pieceToReplace.position[0] & move[1] == pieceToReplace.position[1]) {
                return true;
            }
        }

        return false;
    }
}
