package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    void testInitEmptyBoard() {
        Board board = new Board(false);
        board.initEmptyBoard();
        board.printBoard();
    }
}