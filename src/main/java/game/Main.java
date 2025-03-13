package game;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        GameLoop gameLoop = new GameLoop(board);
        gameLoop.run();
    }
}
