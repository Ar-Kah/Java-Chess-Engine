package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class King extends ChessPiece{
    private boolean hasMoved = false;
    private boolean hasCastled = false;
    private final int[][] kingMoves = new int[][] {
        {1, 0}, {1, 1}, {0, 1}, {-1, 0}, {-1, -1}, {0, -1}
    };

    public King(String color, int[] position) {
        super("K", color, position);
    }

    @Override
    public boolean canMoveTo(ChessPiece target, Board board) {
        List<int[]> moves = getMoves(board);
        List<int[]> unsafeMoves = getUnsafeMoves(board);

        for (int[] move: unsafeMoves) {
            if (move[0] == target.position[0] & move[1] == target.position[1]) {
                return false;
            }
        }

        // check for normal moves
        for (int[] move: moves) {
            if (move[0] == target.position[0] & move[1] == target.position[1]) {
                hasMoved = true;
                return true;
            }
        }

        // check for castling moves
        if (!hasCastled & !hasMoved) {
            List<int[]> castleMoves = getMovesForCastling(board);
            for (int[] move : castleMoves) {
                if (move[0] == target.position[0] & move[1] == target.position[1]) {
                    if (move[1] == 2) {
                        // check the move to the left
                        Rook rook = (Rook) board.board[move[0]][0];
                        board.board[rook.position[0]][rook.position[1]] = new Space(new int[] {move[0], 0});
                        board.board[move[0]][3] = rook;
                        rook.position = new int[] {move[0], 3};
                    } else {
                        // check the move to the right
                        Rook rook = (Rook) board.board[move[0]][7];
                        board.board[rook.position[0]][rook.position[1]] = new Space(new int[] {move[0], 7});
                        board.board[move[0]][5] = rook;
                        rook.position = new int[] {move[0], 5};
                    }
                    hasMoved = true;
                    hasCastled = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<int[]> getMoves(Board board) {

        List<int[]> moves = new ArrayList<>();

        int row = this.position[0];
        int column = this.position[1];

        for (int[] move: kingMoves) {
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

    /**
     * Check the possibility to castle
     * @param board instance of the board
     * @return returns available castle options
     */
    private List<int[]> getMovesForCastling(Board board) {

        List<int[]> castleMoves = new ArrayList<>();
        int[] directions = {1, -1};
        int startingRow = this.color.equals("W") ? 7 : 0;

        for (int direction: directions) {
            int newColumn = direction + this.position[1];
            while(newColumn <= 7 & newColumn >= 0) {
                ChessPiece piece = board.board[startingRow][newColumn];
                if (piece instanceof Bishop | piece instanceof Knight | piece instanceof Queen) {
                    break ; // if there is a bishop or knight on ether side cant castle
                }

                // check if rook has moved
                if (piece instanceof Rook) {
                    if (!(((Rook) piece).hasMoved)) {
                        if (newColumn < 4) {
                            castleMoves.add(new int[] {startingRow, 2}); // if the rook is on the left
                        } else {
                            castleMoves.add(new int[] {startingRow, 6}); // if the rook is on the right
                        }
                    }
                }
                newColumn += direction;
            }
        }
        return castleMoves;
    }

    private List<int[]> getUnsafeMoves(Board board) {
        List<int[]> notSafeMoves = new ArrayList<>();

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                ChessPiece chessPiece = board.board[i][j];
                if (chessPiece.color.equals(this.color)) continue;     // don't calculate the move for same colored pieces
                if (chessPiece instanceof Space) continue;              // can not calculate moves for spaces

                notSafeMoves.addAll(chessPiece.getMoves(board));
            }
        }
        return notSafeMoves;
    }
}
