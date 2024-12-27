package org.example;


public abstract class ChessPiece {
    // position is in format first is the horizontal index (row) and then the vertical index (column)
    protected int[] position;
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
     * @return
     */

    public abstract boolean move(Board board, int[] moveTo, boolean check, boolean checkMate);

}
