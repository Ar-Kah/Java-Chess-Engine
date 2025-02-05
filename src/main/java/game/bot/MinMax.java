package game.bot;

import game.Board;
import game.ChessPiece;
import game.Pawn;

import java.util.ArrayList;
import java.util.List;

public class MinMax {

    private final int DEPTH = 3;
    private final String MAXING = "B";

    public MinMax(Board board) {
        init(board);
    }

    public void init(Board board) {

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        ChessPiece bestPiece = null;


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Board clonedBoard = Board.clone(board);
                ChessPiece piece = clonedBoard.board[i][j];

                if (!piece.getColor().equals(MAXING)) continue;

                List<int[]> moves = new ArrayList<>();
                if (piece instanceof Pawn) {
                    moves = ((Pawn) piece).generateMoves(clonedBoard);
                } else {
                    moves = piece.getMoves(board);
                }

                if (moves == null) continue;

                for (int[] move: moves) {
                    piece.updateBoard(clonedBoard, move[0], move[1], piece.position);

                    int score = minimax(clonedBoard, DEPTH, false);
                    if (score > bestScore) {
                        bestPiece = piece;
                        bestMove = move;
                        bestScore = score;
                    }
                }
            }
        }
        System.out.println("actual move: ");
        assert bestPiece != null;
        bestPiece.updateBoard(board, bestMove[0], bestMove[1], bestPiece.position);
    }

    private int minimax(Board board, int depth, boolean isMaximizing) {
        if (depth == 0) {
            return evaluateBoard(board);
        }
        System.out.println("depth: " + depth % 3 + 1);
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Board boardClone = Board.clone(board);
                    ChessPiece piece = boardClone.board[i][j];

                    if (!piece.getColor().equals(MAXING)) {
                        continue;
                    }

                    List<int[]> moves = new ArrayList<>();
                    if (piece instanceof Pawn) {
                        moves = ((Pawn) piece).generateMoves(boardClone); // special case for pawns
                    } else {
                        moves = piece.getMoves(boardClone); // for others
                    }

                    if (moves == null) continue;

                    for (int[] move: moves) {
                        piece.updateBoard(boardClone, move[0], move[1], piece.position);
                        int eval = minimax(boardClone, depth - 1, false);
                        maxEval = Math.max(eval, maxEval);
                    }
                }
            }

            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Board boardClone = Board.clone(board);
                    ChessPiece piece = boardClone.board[i][j];

                    if (piece.getColor().equals(MAXING)) continue;

                    List<int[]> moves = new ArrayList<>();
                    if (piece instanceof Pawn) {
                        moves = ((Pawn) piece).generateMoves(boardClone);
                    } else {
                        moves = piece.getMoves(boardClone);
                    }

                    if (moves == null) continue;

                    for (int[] move: moves) {
                        piece.updateBoard(boardClone, move[0],move[1], piece.position);
                        int eval = minimax(boardClone, depth - 1, true);
                        minEval = Math.min(eval, minEval);
                    }
                }
            }

            return minEval;

        }
    }

    private int evaluateBoard(Board board) {
        return 0;
    }
}
