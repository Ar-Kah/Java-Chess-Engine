package game;

import java.util.ArrayList;
import java.util.List;

/**
 * the movement of this class it a bit different to the other classes that
 * inherit the ChessPiece class because of the first move of the pawn when
 * it can move 2 steps forward
 */
public class Pawn extends ChessPiece {
    private boolean firstMove = true;

    public Pawn(String color, int[] position) {
        super("P", color, position);
        super.name = "Pawn";
    }

    /**
     * Generate all the possible moves for this pawn.
     *
     * @param board: instance of the game board
     * @return returns all the moves in a list of integers
     */
    private List<int[]> generateMoves(Board board) {
        List<int[]> moves = new ArrayList<>();
        int direction = this.color.equals("W") ? -1 : 1;
        int row = this.position[0];
        int column = this.position[1];

        // single step forward
        if (isValidMove(row + direction, column, board, true)) {
            moves.add(new int[]{row + direction, column});
        }

        // on the first move you can double step forward
        if (isValidMove(row + 2 * direction, column, board, true)) {
            moves.add(new int[]{row + 2 * direction, column});
        }

        // capturing diagonally
        if (isValidMove(row + direction, column + 1, board, false)) {
            moves.add(new int[]{row + direction, column + 1});
        }
        if (isValidMove(row + direction, column - 1, board, false)) {
            moves.add(new int[]{row + direction, column - 1});
        }
        return moves;
    }

    /**
     * Check the validity of move.
     *
     * @param newRow:    Row of target position.
     * @param newColumn: Column of target position.
     * @param board:     Instance of the game board.
     * @param isForward: true if move is forward.
     * @return true if move was valid.
     */
    private boolean isValidMove(int newRow, int newColumn, Board board, boolean isForward) {
        // check bounds of board
        if (newRow < 0 | newColumn < 0 | newRow > 7 | newColumn > 7) {
            return false;
        }

        // can not capture when moving forward
        ChessPiece target = board.board[newRow][newColumn];
        if (isForward & !(target instanceof Space)) {
            return false;
        }

        return !target.color.equals(this.color);
    }

    /**
     * Check if the next move can capture the opponents king
     * @param board: instance of the game board
     * @return true if king is checked
     */
    public boolean isCheckingKing(Board board) {
        // call get moves (for the pawn this is only the capturing moves)
        List<int[]> capturingMoves = getMoves(board);

        for (int[] move: capturingMoves) {
            ChessPiece piece = board.board[move[0]][move[1]];
            if (piece instanceof King & !piece.color.equals(this.color)) {
                System.out.println("checking king");
                return true; // king is checked
            }
        }

        return false;
    }

    /**
     * This method checks if a possible move matches with the player given target position.
     * @param pieceToReplace:   This is the spot where the selected piece would like to move
     * @param board:            Instance of the game board
     * @return true if found a match
     */
    public boolean canMoveTo(ChessPiece pieceToReplace, Board board) {
        // here we use the generate moves to get all the available moves for the pawn
        List<int[]> moves = generateMoves(board);

        for (int[] move : moves) {
            if (move[0] == pieceToReplace.position[0] & move[1] == pieceToReplace.position[1]) {

                // checking piece is captured
                if (board.getCheckingPiece() == pieceToReplace) {
                    board.setCheckingPiece(null);
                    board.setCheck(false);
                }

                // check if first move is used
                if (firstMove) {
                    firstMove = false;
                }

                return true;    // successful move
            }
        }
        return false;
    }

    @Override
    public List<int[]> getMoves(Board board) {
        // generate next capture moves to check if king is checked
        List<int[]> moves = new ArrayList<>();

        int direction = this.color.equals("W") ? -1: 1;
        int row = this.position[0];
        int column = this.position[1];

        if (isValidMove(row + direction, column + 1, board, false)) {
            moves.add(new int[] {row + direction, column + 1});
        }
        if (isValidMove(row + direction, column - 1, board, false)) {
            moves.add(new int[] {row + direction, column - 1});
        }
        return moves;
    }
}
