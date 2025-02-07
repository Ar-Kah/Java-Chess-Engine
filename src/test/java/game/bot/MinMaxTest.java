package game.bot;

import game.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MinMaxTest {
    @Test
    void testInit() {
        Board board = new Board(true);
        board.printBoard();
        Assertions.assertTrue(board.board[6][4].move(board, new int[] {4,4}));
        board.printBoard();
        MinMax minMax = new MinMax(board);
        board.printBoard();
    }
}