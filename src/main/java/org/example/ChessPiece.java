package org.example;


public abstract class ChessPiece {
    // position is in format first is the horizontal index (row) and then the vertical index (column)
    protected int[] position;
    // character could be also named as handle
    protected String character;
    protected String color;
    protected String name;

    public ChessPiece(String gamePeace, String color, int[] position) {
        // white game pieces ar in lowercase
        if (color.equals("W")) {
            this.character = gamePeace.toLowerCase();
        } else {
            this.character = gamePeace;
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
    public final boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        // calculate the distance of current row and column to the wanted spot
        int rowDifferance = Math.abs(this.position[0] - moveTo[0]);
        int columnDifferance = Math.abs(this.position[1] - moveTo[1]);
        ChessPiece piece = board.board[moveTo[0]][moveTo[1]];

        boolean isValid = checkValidMove(rowDifferance, columnDifferance, piece, check, checkMate, board);

        if (!isValid) {
            System.out.println("Invalid move");
            return false;
        }

        // make move visible on game board
        int row = moveTo[0];
        int column = moveTo[1];
        updateBoard(board, row, column);
        return true;

    }

    public abstract boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board);

    /**
     * this method updated the view of the game board after a move
     * @param board pointer to the instance of the game board
     * @param row index row where the piece will be drawn
     * @param column index column where the piece will be drawn
     */
    protected void updateBoard(Board board, int row, int column) {
        // move space to pawns last position
        board.board[this.position[0]][this.position[1]] = new Space(this.position);
        // move piece to new location
        this.position[0] = row;
        this.position[1] = column;
        board.board[row][column] = this;
        board.printBoard();
    }
}
