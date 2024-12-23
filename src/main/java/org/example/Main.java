package org.example;

public class Main {
    /**
     * This is an attempt to make a chess game and on top of it implement a minMax algorithm
     * that the player can play against
     * @param args no arguments
     */
    public static void main(String[] args) {
        // make an instance of the game board
        Board board = new Board();
        board.printBoard();
        // run the game
        GameLogic game = new GameLogic(board);
        game.run();
    }
}
