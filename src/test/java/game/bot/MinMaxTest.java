package game.bot;

import game.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxTest {
    @Test
    void testInit() {
        Board board = new Board(true);
        board.printBoard();
        MinMax minMax = new MinMax(board);
    }
}