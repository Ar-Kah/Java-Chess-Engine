package game;

import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    void testInitEmptyBoard() {
        Board board = new Board(false);
        board.initEmptyBoard();
        board.printBoard();
    }

    @Test
    void boardCloning() {
        Board mainBoard = new Board(true);
        Board clone = Board.clone(mainBoard);
        System.out.println("cloned board");
        clone.printBoard();
        System.out.println("move on cloned board");
        clone.board[1][0].move(clone, new int[] {2, 0});
        System.out.println("main board print");
        mainBoard.printBoard();
    }
}