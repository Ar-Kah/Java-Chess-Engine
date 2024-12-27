package org.example;

public class Pawn extends ChessPiece {

    private boolean firstMove = true;

    public Pawn(String color, int[] position) {
        super("P", color, position);
        super.name = "Pawn";
    }


    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate) {
        // TODO: fix the possibility to move the pawns backwards
        // first move of the pawn
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
            // if piece is moving forward and in front is only empty space
            if (pieceToReplace instanceof Space && columnDifferance == 0) {return true;}
            // when capturing: replaced space can't be empty space, the pawn has to move one row to the left or right and color can't be the same
            return !(pieceToReplace instanceof Space) && columnDifferance == 1 && !pieceToReplace.color.equals(this.color);
        }
    }
}
