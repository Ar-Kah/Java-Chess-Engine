package org.example;

public class King extends ChessPiece{
    public King(String color, int[] position) {
        super("K", color, position);
    }

    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate) {
        // TODO: implement check situations
        if (rowDifferance > 2 | columnDifferance > 2) {
            return false;
        }

        if (!(pieceToReplace instanceof Space) & pieceToReplace.color.equals(this.color)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {
        return false;
    }
}
