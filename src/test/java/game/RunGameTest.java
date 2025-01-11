package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunGameTest {
    @Test
    void testForStaleMate() {
        Board board = new Board();
        board.initEmptyBoard();

        // make a stalemate situation
        board.board[0][0] = new King("W", new int[] {0, 0});
        board.board[2][1] = new King("B", new int[] {2, 1});
        board.board[2][3] = new Queen("B", new int[] {2, 3});
        board.printBoard();
        RunGame game = new RunGame(board);
        Assertions.assertTrue(game.checkForStaleMate());
    }
}