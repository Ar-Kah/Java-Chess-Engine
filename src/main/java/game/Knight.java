package game;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece{
    private final int value = 3;
    private final int[][] knightMoves = new int[][] {
            {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
    };

    private final double[][] placeValueWhite = {
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
            {-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0},
            {-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0},
            {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
            {-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0},
            {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
            {-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0},
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
    };

    private final double[][] placeValueBlack = {
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
            {-4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0},
            {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
            {-3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0},
            {-3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0},
            {-3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0},
            {-4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0},
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
    };

    public Knight(String color, int[] position) {
        super("N", color, position);
        super.name = "Knight";
    }

    @Override
    public boolean canMoveTo(ChessPiece pieceToReplace, Board board) {
        List<int[]> moves = getMoves(board);

        for (int[] move: moves) {
            if (move[0] == pieceToReplace.position[0] & move[1] == pieceToReplace.position[1]) {
                // checking piece is captured
                if (board.getCheckingPiece() == pieceToReplace) {
                    board.setCheckingPiece(null);
                    board.setCheck(false);
                }

                return true; // successful move
            }
        }

        return false;
    }

    @Override
    public List<int[]> getMoves(Board board) {
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
        return moves;
    }

    @Override
    public ChessPiece clone() {
        return new Knight(this.color, this.position.clone());
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public double getPlaceValueWhite(int[] position) {
        return placeValueWhite[position[0]][position[1]];
    }

    @Override
    public double getPlaceValueBlack(int[] position) {
        return placeValueBlack[position[0]][position[1]];
    }
}
