package game;

import java.util.List;

public abstract class ChessPiece {
    public int[] position;    // position is in format first is the horizontal index (row) and then the vertical index (column)
    protected String character;  // character could be also named as handle
    protected String color;      // White or black
    protected String name;

    /**
     * Constructs a Chess piece
     * @param gamePiece     name of the piece
     * @param color         white or black
     * @param position      position where the piece will be placed of the game board
     */
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

        /*
        this method is used to check if your own king is checked after a move.
        if this is the case the move will be flagged invalid, and you will
        have to think of another move that is valid
         */
        if (isKingCheckedAfterMove(board, moveTo)) {
            System.out.println("Invalid move, your king would be checked or is checked currently");
            return false;
        }

        // Perform the move on the game board
        board.board[oldPosition[0]][oldPosition[1]] = new Space(oldPosition);
        board.board[moveTo[0]][moveTo[1]] = this;
        this.position = moveTo;

        /*
        Compared to the other king checking checker method this method is called
        to determine if this move creates a checking of the opponents king.
         */
        if (isCheckingKing(board)) {
            board.setCheckingPiece(this);
            board.setCheck(true);
        } else {
            board.setCheck(false);
            board.setCheckingPiece(null);
        }

        // Update the game board
        updateBoard(board, moveTo[0], moveTo[1], oldPosition);
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

    /**
     * this is a method for pawn to check if it is checking the opponents king after a move
     * @param board current board
     * @return true if checking king
     */
    public boolean isCheckingKing(Board board) {
        List<int[]> moves = this.getMoves(board);
        if (moves == null) return false;
        for (int[] move: moves) {
            ChessPiece piece = board.board[move[0]][move[1]];
            if (piece instanceof King & !piece.color.equals(this.color)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is primarily used to move in the minimax algorithm
     * but is also called in for the player when he/she moves.
     *
     * @param board     Pointer to the instance of the game board
     * @param row       Index row where the piece will be drawn
     * @param column    Index column where the piece will be drawn
     * @param oldPosition Previous position of the moving piece
     */
    public void updateBoard(Board board, int row, int column, int[] oldPosition) {

        // Create a copy of oldPosition for the Space object
        int[] spacePosition = new int[]{oldPosition[0], oldPosition[1]};

        // TODO: fix the en passant bug "will eat random stuff and the en passant piece does not update correctly"
        /* if (this instanceof Pawn && board.enPassantActive) {
            int enPassantRow = this.color.equals("W") ? row + 1 : row - 1;
            if (column == board.enPassant.position[1] && Math.abs(row - oldPosition[0]) == 1) {
                // Remove the pawn that was captured by en passant
                board.board[enPassantRow][column] = new Space(new int[]{enPassantRow, column});
                // Reset en passant state
                board.enPassantActive = false;
                board.enPassant = null;
            }
        } */

        // Move space to pawn's last position
        // board.board[oldPosition[0]][oldPosition[1]] = new Space(spacePosition);

        // Move piece to new location
        this.position[0] = row;
        this.position[1] = column;

        int lastRow = this.color.equals("W") ? 0 : 7;

        if (this instanceof Pawn && lastRow == row && Math.abs(row - oldPosition[0]) == 1) {
            // Pawn promotion to Queen
            board.board[row][column] = new Queen(this.color, new int[]{row, column});
        } else {
            board.board[row][column] = this;
        }
    }

    /**
     * This method checks if moving a piece unintentionally checks your own king
     * @param clonedBoard   cloned board of simulated move
     * @return              true if own king is checked
     */
    private boolean unsafeMove(Board clonedBoard) {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                ChessPiece piece = clonedBoard.board[i][j];
                if (piece.color.equals(this.color) | piece instanceof Space) continue;
                if (piece.isCheckingKing(clonedBoard)) return true;
            }
        }
        return false;
    }

    /**
     * Method for checking if the opponents king is checked after a move
     * @param board     current game board
     * @param moveTo    target place
     * @return is king in check
     */
    private boolean isKingCheckedAfterMove(Board board, int[] moveTo) {
        // Save the original state of the board
        ChessPiece originalTarget = board.board[moveTo[0]][moveTo[1]];
        int[] originalPosition = this.position.clone();

        // Simulate the move
        board.board[originalPosition[0]][originalPosition[1]] = new Space(originalPosition);
        board.board[moveTo[0]][moveTo[1]] = this;
        this.position = moveTo;

        // Check if own king is in check
        King ownKing = Board.findKing(this.color, board);
        boolean isInCheck = false;
        if (ownKing != null) {
            isInCheck = ownKing.isUnderAttack(board);
        }

        // Revert the move
        board.board[originalPosition[0]][originalPosition[1]] = this;
        board.board[moveTo[0]][moveTo[1]] = originalTarget;
        this.position = originalPosition;

        return isInCheck;
    }


    /**
     * Checks if the king is under attack after a move.
     * This was made so that we can detect if an invalid move would be
     * made so that our own king would be in danger.
     *
     * @param board board after a move has been simulated
     * @return true if something could check the king
     */
    public boolean isUnderAttack(Board board) {
        for (int i = 0; i < board.board.length; i++)
        {
            for (int j = 0; j < board.board[i].length; j++)
            {
                ChessPiece piece = board.board[i][j];

                // Check if the piece is an opponent and can attack the king's position
                if (piece != null && !piece.color.equals(this.color)) {

                    List<int[]> moves = piece.getMoves(board);
                    if (moves != null) {
                        for (int[] move : moves) {
                            if (move[0] == this.position[0] && move[1] == this.position[1]) {
                                return true; // King is under attack
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the value of the given chess piece for the minimax evaluation function.
     * @return value
     */
    public abstract int getValue();

    /**
     * Returns the weight of the placement of given white piece
     * @param position position of the chess piece
     * @return value
     */
    public abstract double getPlaceValueWhite(int[] position);

    /**
     * Returns the weight of the placement of given black piece
     * @param position position of the chess piece
     * @return value
     */
    public abstract double getPlaceValueBlack(int[] position);

    /**
     * Clone the given chess piece.
     * @return cloned piece
     */
    public abstract ChessPiece clone();

    /**
     * Getter for the color of the piece (white or black).
     * @return the color
     */
    public String getColor() {
        return color;
    }
}
