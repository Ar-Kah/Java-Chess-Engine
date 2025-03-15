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
        if (!isValidMove(board, moveTo)) {
            System.out.println("Invalid move");
            return false;
        }

        // Perform the move
        performMove(board, moveTo);

        // Check if the opponent's king is in check
        updateCheckStatus(board);

        return true;
    }

    private boolean isValidMove(Board board, int[] moveTo) {
        ChessPiece target = board.board[moveTo[0]][moveTo[1]];

        if (!canMoveTo(target, board)) return false;
        if (isKingCheckedAfterMove(board, moveTo)) {
            System.out.println("Invalid move, your king would be checked.");
            return false;
        }

        return true;
    }

    private void performMove(Board board, int[] moveTo) {
        int[] oldPosition = this.position.clone();

        board.board[oldPosition[0]][oldPosition[1]] = new Space(oldPosition);
        board.board[moveTo[0]][moveTo[1]] = this;
        this.position = moveTo;

        updateBoard(board, moveTo[0], moveTo[1], oldPosition);
    }

    private void updateCheckStatus(Board board) {
        if (isCheckingKing(board)) {
            board.setCheckingPiece(this);
            board.setCheck(true);
        } else {
            board.setCheck(false);
            board.setCheckingPiece(null);
        }
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
     * but is also called in for the player when he/she moves
     *
     * @param board     Pointer to the instance of the game board
     * @param row       Index row where the piece will be drawn
     * @param column    Index column where the piece will be drawn
     * @param oldPosition Previous position of the moving piece
     */
    public void updateBoard(Board board, int row, int column, int[] oldPosition) {

        // I know this is done twice when the player moves a piece, but to not get bugs when the bot castles we need to call this. I could not think of anything else for now.
        if (this instanceof King) {
            canMoveTo(board.board[row][column], board);
        }

        // Create a copy of oldPosition for the Space object
        int[] spacePosition = new int[]{oldPosition[0], oldPosition[1]};

        if (this instanceof Pawn && board.enPassantActive) {
            int enPassantRow = this.color.equals("W") ? row + 1 : row - 1;
            if (column == board.enPassant.position[1] && Math.abs(row - oldPosition[0]) == 1) {
                board.board[enPassantRow][column] = new Space(new int[]{enPassantRow, column});
                board.enPassantActive = false;
                board.enPassant = null;
            }
        }


        // Move space to pawn's last position
        board.board[oldPosition[0]][oldPosition[1]] = new Space(spacePosition);

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
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ChessPiece piece = clonedBoard.board[i][j];
                if (piece.color.equals(this.color) | piece instanceof Space) continue;
                if (piece.isCheckingKing(clonedBoard)) return true;
            }
        }
        return false;
    }

    public boolean isKingCheckedAfterMove(Board board, int[] moveTo) {
        // Save the original state of the board
        ChessPiece originalTarget = board.board[moveTo[0]][moveTo[1]];
        int[] originalPosition = this.position.clone();

        // Simulate the move
        board.board[originalPosition[0]][originalPosition[1]] = new Space(originalPosition);
        board.board[moveTo[0]][moveTo[1]] = this;
        this.position = moveTo;

        // Check if own king is in check
        King ownKing = findKing(this.color, board);
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

    public static King findKing(String color, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.board[i][j];
                if (piece instanceof King && piece.color.equals(color)) {
                    return (King) piece;
                }
            }
        }
        return null; // If no king is found (shouldn't happen in a valid game)
    }
    public boolean isUnderAttack(Board board) {
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board[i].length; j++) {
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

    public abstract int getValue();

    public abstract double getPlaceValueWhite(int[] position);
    public abstract double getPlaceValueBlack(int[] position);

    public abstract ChessPiece clone();

    public String getColor() {
        return color;
    }
}
