package org.example;

public class Pawn extends ChessPiece {

    private boolean firstMove = true;

    public Pawn(String color, int[] position) {
        super("P", color, position);
        super.name = "Pawn";
    }

    @Override
    public boolean move(Board board, int[] moveTo, boolean check, boolean checkMate) {
        // calculate the distance of current row and column to the wanted spot
        // System.out.println("Row numbers: " + this.position[0] + " " + moveTo[0]);
        // System.out.println("Column numbers: " + this.position[1] + " " + moveTo[1]);
        int rowDifferance = Math.abs(this.position[0] - moveTo[0]);
        int columnDifferance = Math.abs(this.position[1] - moveTo[1]);
        ChessPiece piece = board.board[moveTo[0]][moveTo[1]];

        boolean isValid = checkValidMove(rowDifferance, columnDifferance, piece, check, checkMate);

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


    private boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate) {
        // first move of the pawn
        // System.out.println(rowDifferance + " "+ columnDifferance);
        // System.out.println(firstMove + " " + columnDifferance + " " + rowDifferance);
        if (firstMove && rowDifferance <=2 && columnDifferance == 0) {
            firstMove = false;
            return true;
        }
        // after the first move
        else {
            // if pawn is not moving forward 1. Move is invalid
            if (rowDifferance != 1) {return false;}
            // moving horizontally more than 2 is prohibited
            if (columnDifferance > 1) {return false;}
            // if
            if (pieceToReplace instanceof Space && columnDifferance == 0) {return true;}
            // else when not instance of Space or row is != 0
            return !(pieceToReplace instanceof Space) && columnDifferance == 1 && !pieceToReplace.color.equals(this.color);
        }
    }

    public void updateBoard(Board board, int row, int column) {
        // move space to pawns last position
        board.board[this.position[0]][this.position[1]] = new Space(this.position);
        // move piece to new location
        this.position[0] = row;
        this.position[1] = column;
        board.board[row][column] = this;
        board.printBoard();
    }
}
