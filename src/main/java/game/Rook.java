package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Rook class for calculating moves and storing important information
 */
public class Rook extends ChessPiece {
    public int value = 5;
    public boolean hasMoved = false;
    public Rook(String color, int[] position) {
        super("R", color, position);
    }

    @Override
    public boolean canMoveTo(ChessPiece pieceToReplace, Board board) {
        List<int[]> moves = getMoves(board);

        // Determine if a specific move is valid (optional)
        for (int[] move : moves) {
            if (pieceToReplace.position[0] == move[0] && pieceToReplace.position[1] == move[1]) {

                // checking piece is captured
                if (board.getCheckingPiece() == pieceToReplace) {
                    board.setCheckingPiece(null);
                    board.setCheck(false);
                }
                this.hasMoved = true;
                return true; // Valid move
            }
        }

        return false;
    }

    /**
     * Method to calculate all possible moves for a rook
     * This method was partially optimized with the help of ChatGPT
     *
     * @param board: instance of the game board
     * @return list of moves
     */
    public List<int[]> getMoves(Board board) {
        List<int[]> moves = new ArrayList<>();
        int row = this.position[0];
        int column = this.position[1];

        // Check each direction independently
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newColumn = column + dir[1];

            while (newRow >= 0 && newRow <= 7 && newColumn >= 0 && newColumn <= 7) {
                if (!(board.board[newRow][newColumn] instanceof Space)) {
                    // If it's an opponent's piece, add move and stop
                    if (!board.board[newRow][newColumn].color.equals(this.color)) {
                        moves.add(new int[]{newRow, newColumn});
                    }
                    break; // Path is blocked
                }
                moves.add(new int[]{newRow, newColumn});
                newRow += dir[0];
                newColumn += dir[1];
            }
        }
        return moves;
    }

    @Override
    public ChessPiece clone() {
        return new Rook(this.color, this.position.clone());
    }
}
