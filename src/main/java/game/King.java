package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends ChessPiece{
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

}
