package game;

import java.util.List;

public abstract class ChessPiece {
    public int[] position;    // position is in format first is the horizontal index (row) and then the vertical index (column)
    protected String character;  // character could be also named as handle
    protected String color;      // White or black
    protected String name;

    public ChessPiece(String gamePiece, String color, int[] position) {
        // white game pieces ar in lowercase
        if (color.equals("W")) {
            this.character = gamePiece.toLowerCase();
        } else {
            this.character = gamePiece;
        }
        this.color = color;
        this.position = position;
    }

    public String toString() {
        return character;
    }

    /**
     * abstract method used to move all the different pieces in the game
     *
     * @param board  the instance of the game board with all the pieces
     * @param moveTo coordinates where the player wants to move the given piece
     * @return return true if the move was good
     */
    public final boolean move(Board board, int[] moveTo) {
        int[] oldPosition = this.position;
        ChessPiece target = board.board[moveTo[0]][moveTo[1]];

        boolean isValid = canMoveTo(target, board);

        if (!isValid) {
            System.out.println("Invalid move");
            return false;
        }

        // Temporarily apply the move
        board.board[oldPosition[0]][oldPosition[1]] = new Space(oldPosition);
        board.board[moveTo[0]][moveTo[1]] = this;
        this.position = moveTo;

        // Check if the king is still in check
        if (board.isCheck() && board.getCheckingPiece().isCheckingKing(board)) {
            // Revert the move
            board.board[oldPosition[0]][oldPosition[1]] = this;
            board.board[moveTo[0]][moveTo[1]] = target; // Reuse targetPiece
            this.position = oldPosition;

            System.out.println("King is still checked, make another move");
            return false;
        } else if (board.isCheck()) {
            board.setCheck(false);
            board.setCheckingPiece(null);
        }

        // make move visible on game board
        int row = moveTo[0];
        int column = moveTo[1];
        updateBoard(board, row, column, oldPosition);

        // check if the opponent's king is in check
        if (isCheckingKing(board)) {
            board.setCheckingPiece(this);
            board.setCheck(true);
        }
        return true;

    }

    /**
     * This method checks if the players given target position matches with a possible move
     * (abstract because of pawns first move logic)
     * @param pieceToReplace: This is the spot where the selected piece would like to move
     * @param board: Instance of the game board
     * @return true when valid move
     */
    public abstract boolean canMoveTo(ChessPiece pieceToReplace, Board board);

    /**
     * returns all the possible moves for given piece in its current location
     * @param board: instance of the game board
     * @return list of available moves
     */
    public abstract List<int[]> getMoves(Board board);

    public boolean isCheckingKing(Board board) {
        List<int[]> moves = this.getMoves(board);

        for (int[] move: moves) {
            ChessPiece piece = board.board[move[0]][move[1]];
            if (piece instanceof King & !piece.color.equals(this.color)) {
                System.out.println("checking king");
                return true;
            }
        }

        return false;
    }

    /**
     * This method updated the view of the game board after a move
     * @param board     pointer to the instance of the game board
     * @param row       index row where the piece will be drawn
     * @param column    index column where the piece will be drawn
     */
    protected void updateBoard(Board board, int row, int column, int[] oldPosition) {
        // Create a copy of oldPosition for the Space object
        int[] spacePosition = new int[]{oldPosition[0], oldPosition[1]};

        // Move space to pawn's last position
        board.board[oldPosition[0]][oldPosition[1]] = new Space(spacePosition);

        // Move piece to new location
        this.position[0] = row;
        this.position[1] = column;
        board.board[row][column] = this;

        // Print the updated board
        board.printBoard();
    }
}
