package game;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece{
    public int value = 3;
    public Bishop(String color, int[] position) {
        super("B", color, position);
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

                return true; // Valid move
            }
        }

        return false;
    }

    @Override
    public List<int[]> getMoves(Board board) {
        List<int[]> moves = new ArrayList<>();
        int row = this.position[0];
        int column = this.position[1];

        // Check each direction independently
        int[][] directions = {{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
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

}
