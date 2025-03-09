package game.bot;

import game.Board;
import game.ChessPiece;
import game.Pawn;
import game.Space;

import java.util.ArrayList;
import java.util.List;

public class MinMax {

    private final int DEPTH = 3; // depth of search for the minimax
    private final String MAXING = "B"; // black is the maximizing player

    public MinMax(Board board) {
        init(board);
    }

    /**
     * The initial call for the minimax algo.
     * @param board instance of the played board
     */
    public void init(Board board) {

        double bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        ChessPiece bestPiece = null;


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Board clonedBoard = Board.clone(board);
                ChessPiece piece = clonedBoard.board[i][j];

                if (!piece.getColor().equals(MAXING)) continue;

                // we need to call a different method for pawn
                List<int[]> moves = new ArrayList<>();
                if (piece instanceof Pawn) {
                    moves = ((Pawn) piece).generateMoves(clonedBoard);
                } else {
                    moves = piece.getMoves(board);
                }

                if (moves == null) continue;

                for (int[] move: moves) {
                    ChessPiece originalPiece = clonedBoard.board[move[0]][move[1]];
                    int[] originalPosition = {piece.position[0], piece.position[1]};

                    piece.updateBoard(clonedBoard, move[0], move[1], piece.position);

                    double score = minimax(clonedBoard, DEPTH, Double.MIN_VALUE, Double.MAX_VALUE, false);

                    // Restore state after simulation
                    clonedBoard.board[move[0]][move[1]] = originalPiece;
                    clonedBoard.board[originalPosition[0]][originalPosition[1]] = piece;
                    piece.position = originalPosition;
                    if (score > bestScore) {
                        bestPiece = piece;
                        bestMove = move;
                        bestScore = score;
                    }
                }
            }
        }
        assert bestPiece != null;
        bestPiece.move(board, bestMove);
    }

    /**
     * Minimax with alpha beta pruning for chess decision-making
     * @param board         current board state
     * @param depth         depth of search
     * @param alpha         min integer
     * @param beta          max integer
     * @param isMaximizing  black is maxing
     * @return min or max eval
     */
    private double minimax(Board board, int depth, double alpha, double beta, boolean isMaximizing) {

        if (depth == 0) {
            return evaluateBoard(board);
        }

        if (isMaximizing) {
            double maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Board boardClone = Board.clone(board);
                    ChessPiece piece = boardClone.board[i][j];

                    if (!piece.getColor().equals(MAXING)) {
                        continue;
                    }

                    // we need to call a different method for pawn
                    List<int[]> moves = new ArrayList<>();
                    if (piece instanceof Pawn) {
                        moves = ((Pawn) piece).generateMoves(boardClone); // special case for pawns
                    } else {
                        moves = piece.getMoves(boardClone); // for others
                    }

                    if (moves == null) continue;

                    for (int[] move: moves) {
                        ChessPiece originalPiece = boardClone.board[move[0]][move[1]];
                        int[] originalPosition = {piece.position[0], piece.position[1]};

                        piece.updateBoard(boardClone, move[0], move[1], piece.position);
                        double eval = minimax(boardClone, depth - 1, alpha, beta, false);
                        maxEval = Math.max(eval, maxEval);

                        // Restore state after simulation
                        boardClone.board[move[0]][move[1]] = originalPiece;
                        boardClone.board[originalPosition[0]][originalPosition[1]] = piece;
                        piece.position = originalPosition;

                        if (maxEval > beta) break; // beta cutoff
                        alpha = Math.max(alpha, maxEval);
                    }
                }
            }
            return maxEval;

        } else {
            double minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Board boardClone = Board.clone(board);
                    ChessPiece piece = boardClone.board[i][j];

                    if (piece.getColor().equals(MAXING)) continue;

                    // we need to call a different method for pawn
                    List<int[]> moves = new ArrayList<>();
                    if (piece instanceof Pawn) {
                        moves = ((Pawn) piece).generateMoves(boardClone);
                    } else {
                        moves = piece.getMoves(boardClone);
                    }

                    if (moves == null) continue;

                    for (int[] move: moves) {
                        ChessPiece originalPiece = boardClone.board[move[0]][move[1]];
                        int[] originalPosition = {piece.position[0], piece.position[1]};

                        piece.updateBoard(boardClone, move[0],move[1], piece.position);
                        double eval = minimax(boardClone, depth - 1, alpha, beta, true);
                        minEval = Math.min(eval, minEval);

                        // Restore state after simulation
                        boardClone.board[move[0]][move[1]] = originalPiece;
                        boardClone.board[originalPosition[0]][originalPosition[1]] = piece;
                        piece.position = originalPosition;

                        if (minEval < alpha) break;
                        beta = Math.min(beta, minEval);
                    }
                }
            }
            return minEval;
        }
    }

    /**
     * Evaluation function for the board status
     * @param board the board
     * @return value
     */
    private double evaluateBoard(Board board) {
        double blackScore = 0;
        double whiteScore = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.board[i][j];

                if (piece instanceof Space) continue;

                if (piece.getColor().equals("B")) {
                    blackScore += piece.getPlaceValueBlack(piece.position);
                    blackScore += piece.getValue();
                } else {
                    whiteScore -= piece.getPlaceValueWhite(piece.position);
                    whiteScore += piece.getValue();
                }
            }
        }
        return blackScore - whiteScore;
    }
}
