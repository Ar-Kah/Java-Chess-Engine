package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testInitEmptyBoard() {
        Board board = new Board();
        board.initEmptyBoard();
        board.printBoard();
    }
}