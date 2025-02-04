package game.bot;

import game.Board;
import game.ChessPiece;
import game.Main;

import java.util.List;

public class MinMax {

    public MinMax() {

    }

    public static int minimax(Board board, int depth, boolean isMaximizingPlayer, String maximizingColor) {
        if (depth == 0 || board.isCheckMate() || board.isStaleMate()) {
            return evaluateBoard(board);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    ChessPiece piece = board.board[row][column];
                    if (!piece.getColor().equals(maximizingColor)) {
                        continue;
                    }
                    Board clonedBoard = board.clone();

                    List<int[]> moves = piece.getMoves(clonedBoard);
                    if (moves == null) continue;

                    for (int[] move : piece.getMoves(clonedBoard)) {
                        piece.move(clonedBoard, move);
                        int eval = minimax(clonedBoard, depth - 1, false, maximizingColor);
                        maxEval = Math.max(maxEval, eval);
                    }
                }
            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    ChessPiece piece = board.board[row][column];
                    if (piece.getColor().equals(maximizingColor)) {
                        continue;
                    }
                    Board clonedBoard = board.clone();

                    List<int[]> moves = piece.getMoves(clonedBoard);
                    if (moves == null) continue;

                    for (int[] move : moves) {
                        piece.move(clonedBoard, move);
                        int eval = minimax(clonedBoard, depth - 1, true, maximizingColor);
                        minEval = Math.min(minEval, eval);
                    }
                }
            }
            return minEval;
        }
    }

    private static int evaluateBoard(Board board) {
        return 0;
    }
}
