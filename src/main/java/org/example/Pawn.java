package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends ChessPiece {

    private boolean firstMove = true;

    public Pawn(String color, int[] position) {
        super("P", color, position);
        super.name = "Pawn";
    }

    public int[][] getValidMoves() {
        int[][] moves = new int[0][];
        if (this.color.equals("W") & firstMove) {
            moves = new int[][]{{-2, 0}, {-1, 0}};
        }
        if (this.color.equals("B") & firstMove) {
            moves = new int[][]{{2, 0}, {1, 0}};
        }
        if (this.color.equals("W") & !firstMove) {
            moves = new int[][]{{-1, -1}, {-1, 1}, {-1, 0}};
        }
        if (this.color.equals("B") & !firstMove) {
            moves = new int[][]{{1, -1}, {1, 1}, {1, 0}};
        }
        return moves;
    }

    public boolean checkValidMove(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate, Board board) {

        List<int[]> moves = new ArrayList<>();
        int[][] pawnMoves = getValidMoves();

        int row = this.position[0];
        int column = this.position[1];

        for (int[] move: pawnMoves) {
            int newRow = row + move[0];
            int newColumn = column + move[1];

            if (newRow > 7 & newColumn > 7) {
                continue;
            }
            ChessPiece piece = board.board[newRow][newColumn];
            if (piece.color.equals(this.color)) {
                continue;
            }
            moves.add(new int[]{newRow, newColumn});
        }
        System.out.println(Arrays.toString(pieceToReplace.position));
        for (int[] move: moves)  {
            System.out.println(Arrays.toString(move));
            if (move[0] == pieceToReplace.position[0] & move[1] == pieceToReplace.position[1]) {
                return true;
            }
        }

        return false;
    }


    public boolean storedMethod(int rowDifferance, int columnDifferance, ChessPiece pieceToReplace, boolean check, boolean checkMate) {
        // check for color if pawn is trying to move back
        if (this.color.equals("W") & this.position[0] <= pieceToReplace.position[0]) {
            return false;
        } else if (this.color.equals("B") & this.position[0] >= pieceToReplace.position[0]) {
            return false;
        }

        // first move of the pawn
        if (firstMove && rowDifferance <=2 && columnDifferance == 0) {
            if (this.color.equals(pieceToReplace.color)) {return false;}
            firstMove = false;
            return true;
        }
        // after the first move
        else {
            // if pawn is not moving forward 1. Move is invalid
            if (rowDifferance != 1) {return false;}
            // can not move to a place where the same color piece is already
            // moving horizontally more than 2 is prohibited
            if (columnDifferance > 1) {return false;}
            // if piece is moving forward and in front is only empty space
            if (pieceToReplace instanceof Space && columnDifferance == 0) {return true;}
            // when capturing: replaced space can't be empty space, the pawn has to move one row to the left or right and color can't be the same
            return !(pieceToReplace instanceof Space) && columnDifferance == 1 && !pieceToReplace.color.equals(this.color);
        }
    }
}
