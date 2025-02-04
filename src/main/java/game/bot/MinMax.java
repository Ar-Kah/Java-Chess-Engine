package game.bot;

import game.Board;
import game.ChessPiece;
import game.Main;

import java.util.List;
import java.util.Arrays;

public class MinMax {

    public static void findBestMove(Board board, String maximizingColor) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        ChessPiece bestPiece = null;

        // Iterate over all pieces and moves
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board.board[row][col];
                if (piece == null || !piece.getColor().equals(maximizingColor)) continue;

                List<int[]> possibleMoves = piece.getMoves(board);
                if (possibleMoves == null) continue;

                for (int[] move : possibleMoves) {
                    Board clonedBoard = board.clone();

                    // Simulate the move
                    piece.simulateMove(clonedBoard, move);

                    // Evaluate using minimax
                    int score = minimax(clonedBoard, 3, false, maximizingColor);

                    // Keep track of the best move
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = move;
                        bestPiece = piece;
                    }
                }
            }
        }

        if (bestMove != null && bestPiece != null) {
            System.out.println("Best move for " + bestPiece + ": " + Arrays.toString(bestMove));
        }
        assert bestPiece != null;
        bestPiece.move(board, bestMove);
    }

    public static int minimax(Board board, int depth, boolean isMaximizingPlayer, String maximizingColor) {
        if (depth == 0 || board.isCheckMate() || board.isStaleMate()) {
            return evaluateBoard(board);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ChessPiece piece = board.board[row][col];
                    if (piece == null || !piece.getColor().equals(maximizingColor)) continue;

                    List<int[]> moves = piece.getMoves(board);
                    if (moves == null) continue;

                    for (int[] move : moves) {
                        Board clonedBoard = board.clone();
                        piece.simulateMove(clonedBoard, move);
                        int eval = minimax(clonedBoard, depth - 1, false, maximizingColor);
                        maxEval = Math.max(maxEval, eval);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ChessPiece piece = board.board[row][col];
                    if (piece == null || piece.getColor().equals(maximizingColor)) continue;

                    List<int[]> moves = piece.getMoves(board);
                    if (moves == null) continue;

                    for (int[] move : moves) {
                        Board clonedBoard = board.clone();
                        piece.simulateMove(clonedBoard, move);
                        int eval = minimax(clonedBoard, depth - 1, true, maximizingColor);
                        minEval = Math.min(minEval, eval);
                    }
                }
            }
            return minEval;
        }
    }

    private static int evaluateBoard(Board board) {
        // Placeholder evaluation function
        // Example logic: positive for white advantage, negative for black advantage
        int evaluationScore = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board.board[row][col];
                if (piece == null) continue;

                int pieceValue = getPieceValue(piece);
                evaluationScore += piece.getColor().equals("W") ? pieceValue : -pieceValue;
            }
        }

        return evaluationScore;
    }

    private static int getPieceValue(ChessPiece piece) {
        switch (piece.toString().toLowerCase()) {
            case "p": return 1;    // Pawn
            case "n": return 3;    // Knight
            case "b": return 3;    // Bishop
            case "r": return 5;    // Rook
            case "q": return 9;    // Queen
            case "k": return 100;  // King
            default: return 0;
        }
    }
}
