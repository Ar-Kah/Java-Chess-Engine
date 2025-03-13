package game;


/*
This is work of Aaro Karhu
Started on day 13.3.2025
 */
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        GameLoop gameLoop = new GameLoop(board);
        gameLoop.run();
    }
}
