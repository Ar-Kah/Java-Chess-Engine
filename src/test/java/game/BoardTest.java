package game;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void checkingLogicTest() {
        Board board = new Board(false);
        board.initEmptyBoard();

        board.board[6][5] = new Pawn("W", new int[] {6, 5});
        board.board[6][6] = new Pawn("W", new int[] {6, 6});
        board.board[5][7] = new Pawn("W", new int[] {5, 7});
        board.board[7][6] = new King("W", new int[] {7, 6});
        board.board[1][1] = new Rook("B", new int[] {1, 1});
        System.out.println(board.board[6][7].color);
        board.printBoard();
        board.board[1][1].move(board, new int[] {7, 1});
        List<int[]> moves = board.board[7][6].getMoves(board);
        board.board[7][6].move(board, new int[] {6, 7});

        board.printBoard();
    }
}