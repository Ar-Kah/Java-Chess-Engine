package game;

/**
 * @author Aaro Karhu
 * Email: aaro.karhu19@gmail.com
 * University of Tampere
 */
public class Main {
    /**
     * This is an attempt to make a chess game and on top of it implement a minMax algorithm
     * that the player can play against
     * @param args no arguments
     */
    public static void main(String[] args) {
        // make an instance of the game board
        Board board = new Board(true);
        board.printBoard();
        // run the game
        RunGame game = new RunGame(board);
        game.run();
    }
}
