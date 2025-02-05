package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends ChessPiece{
    public int value = 0;
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
        int rowForCastling = this.color.equals("W") ? 7 : 0;

        for (int[] move: moves) {
            // check if move hit the target position
            if (move[0] == target.position[0] & move[1] == target.position[1]) {
                // check if move was for castling
                if (move[0] == rowForCastling & move[1] == 2) {
                    Rook rook = (Rook) board.board[move[0]][0];
                    board.board[rook.position[0]][rook.position[1]] = new Space(new int[] {move[0], 0});
                    board.board[move[0]][3] = rook;
                    rook.position = new int[] {move[0], 3};
                    hasCastled = true;

                } else if (move[0] == rowForCastling & move[1] == 6) {
                    Rook rook = (Rook) board.board[move[0]][7];
                    board.board[rook.position[0]][rook.position[1]] = new Space(new int[] {move[0], 7});
                    board.board[move[0]][5] = rook;
                    rook.position = new int[] {move[0], 5};
                    hasCastled = true;
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
            if (piece.color.equals(this.color)) continue;
            moves.add(new int[]{newRow, newColumn});
        }

        // get castling moves and add them if there are any and remove unsafe moves
        List<int[]> castlingMoves = getMovesForCastling(board);
        List<int[]> unsafeMoves = getUnsafeMoves(board);
        moves.addAll(castlingMoves);
        moves.removeIf(move -> unsafeMoves.stream().anyMatch(unsafe -> Arrays.equals(move, unsafe)));

        return moves;
    }

    /**
     * Check the possibility to castle
     * @param board instance of the board
     * @return returns available castle options
     */
    private List<int[]> getMovesForCastling(Board board) {

        if (hasCastled) return new ArrayList<>();
        if (hasMoved) return new ArrayList<>();

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
        /*
        in this method I have used AI to help me solve a stack overflow problem
        which came from an infinite recursion when checking for moves
        from the other king
         */

        List<int[]> unsafeMoves = new ArrayList<>();

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                ChessPiece chessPiece = board.board[i][j];
                if (chessPiece.color.equals(this.color)) continue;  // Skip same-colored pieces
                if (chessPiece instanceof Space) continue;          // Skip empty spaces

                // Skip the opposing King's moves
                if (chessPiece instanceof King) {
                    // Opponent King: Add only squares directly adjacent to it, skipping recursion
                    unsafeMoves.addAll(getAdjacentSquares(chessPiece.position));
                    continue;
                }

                // For other pieces, calculate moves normally
                unsafeMoves.addAll(chessPiece.getMoves(board));
            }
        }

        return unsafeMoves;
    }

    private List<int[]> getAdjacentSquares(int[] position) {
        List<int[]> adjacentSquares = new ArrayList<>();
        int row = position[0];
        int column = position[1];

        for (int[] move : kingMoves) {
            int newRow = row + move[0];
            int newColumn = column + move[1];

            // Check bounds of the board
            if (newRow < 0 || newRow > 7 || newColumn < 0 || newColumn > 7) continue;

            adjacentSquares.add(new int[]{newRow, newColumn});
        }

        return adjacentSquares;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public ChessPiece clone() {
        King clone = new King(this.color, this.position.clone());
        clone.hasCastled = this.hasCastled;
        clone.hasMoved = this.hasMoved;
        return clone;
    }
}
