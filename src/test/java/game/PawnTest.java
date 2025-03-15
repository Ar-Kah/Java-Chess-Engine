package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PawnTest {

    @Test
    void isCheckingKing() {
        Board board = new Board(false);
        board.initEmptyBoard();
        board.board[3][4] = new King("B", new int[] {3, 4});
        board.board[5][3] = new Pawn("W", new int[] {5, 3});
        // is not checking
        Assertions.assertFalse(board.board[5][3].isCheckingKing(board));
        board.printBoard();
        // is checking
        Assertions.assertTrue(board.board[5][3].move(board, new int[] {4, 3}));
    }

    @Test
    void canMoveTo() {
        Board board = new Board(false);
        board.initEmptyBoard();
        // init black pawns
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("B", new int[]{1, i});
        }
        // init white pawns
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("W", new int[]{6, i});
        }

        board.printBoard();
    }

    @Test
    void enPassant() {
        Board board = new Board(false);
        board.initEmptyBoard();

        board.board[6][0] = new Pawn("W", new int[] {6, 0});
        board.board[6][1] = new Pawn("W", new int[] {6, 1});
        board.board[3][0] = new Pawn("B", new int[] {3, 0});
        board.board[4][1] = new Pawn("B", new int[] {4, 1});
        board.printBoard();

        Assertions.assertTrue(board.board[6][0].move(board, new int[] {4, 0}));
        System.out.println("En passant is active: " + board.enPassantActive);
        // test enPassant
        Assertions.assertTrue(board.board[4][1].move(board, new int[] {5, 0}));
    }

    @Test
    void enPassantWhite() {
        Board board = new Board(false);
        board.initEmptyBoard();
        System.out.println("Testing en passant from white");
        board.board[1][0] = new Pawn("B", new int[] {1, 0});
        board.board[1][1] = new Pawn("B", new int[] {1, 1});
        board.board[4][0] = new Pawn("W", new int[] {4, 0});
        board.board[3][1] = new Pawn("W", new int[] {3, 1});

        board.printBoard();
        Assertions.assertTrue(board.board[1][0].move(board, new int[] {3, 0}));
        board.printBoard();
        Assertions.assertTrue(board.board[3][1].move(board, new int[] {2, 0}));
        board.printBoard();
        System.out.println("_Test completed_");
    }

    @Test
    void pawnToQueen() {
        Board board = new Board(false);
        board.initEmptyBoard();

        board.board[1][0] = new Pawn("W", new int[] {1, 0});
        board.printBoard();
        board.board[1][0].move(board, new int[] {0, 0});
        Assertions.assertTrue(board.board[0][0] instanceof Queen);
        Assertions.assertTrue(board.board[0][0].move(board, new int[] {2, 2}));
    }


    @Test
    void moveTest() {
        Board board = new Board(true);
        board.board[6][3].move(board, new int[] {4, 3});
        board.board[1][3].move(board, new int[] {3, 3});
        board.board[6][4].move(board, new int[] {4, 4});
        board.board[1][4].move(board, new int[] {3, 4});
        board.printBoard();
        board.board[4][4].move(board, new int[] {3, 3});
        board.printBoard();
    }
}