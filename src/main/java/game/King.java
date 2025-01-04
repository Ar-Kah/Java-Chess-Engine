package game;

import java.util.ArrayList;
import java.util.Arrays;
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
    public boolean canMoveTo(ChessPiece pieceToReplace, Board board) {
        List<int[]> moves = getMoves(board);

        for (int[] move: moves) {
            System.out.println(Arrays.toString(move));
            if (move[0] == pieceToReplace.position[0] & move[1] == pieceToReplace.position[1]) {

                // checking piece is captured
                if (board.getCheckingPiece() == pieceToReplace) {
                    board.setCheckingPiece(null);
                    board.setCheck(false);
                }
                hasMoved = true;
                return true;
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
    private List<int[]> canCastle(Board board) {
        // before doing anything check if the king has already castled to save resources
        if (hasCastled) return null;

        List<int[]> castleMoves = new ArrayList<>();

        // if the kings position is not on the last row with white king can't castle
        if (!(this.position[0] == 7 & this.color.equals("W"))) return null;

        int[] directions = {1, -1};
        int row = this.position[0];

        for (int direction: directions) {
            int newColumn = direction + this.position[1];
            while(newColumn <= 7 & newColumn >= 0) {
                ChessPiece piece = board.board[row][newColumn];
                if (piece instanceof Bishop | piece instanceof Knight) {
                    break; // if there is a bishop or knight on ether side cant castle
                }
                if (piece instanceof Rook & piece.position[1] == 7) {
                    castleMoves.add(new int[] {row, 6});
                }

                newColumn += direction;
            }
        }

        return castleMoves;
    }
}
