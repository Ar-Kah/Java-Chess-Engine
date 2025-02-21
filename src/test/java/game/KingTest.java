package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class KingTest {
    @Test
    void testWhiteCastlingRight() {
        Board board = new Board(false);
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("W", new int[]{6, i}); // white pawns
        }

        board.board[7][0] = new Rook("W", new int[] {7, 0});
        board.board[7][7] = new Rook("W", new int[] {7, 7});
        board.board[7][4] = new King("W", new int[] {7, 4});
        board.printBoard();     // try to move white king to the right
        Assertions.assertTrue(board.board[7][4].move(board, new int[] {7, 6}));
        board.printBoard();

    }

    @Test
    void testWhiteCastlingLeft() {
        Board board = new Board(false);
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("W", new int[]{6, i}); // white pawns
        }

        board.board[7][0] = new Rook("W", new int[] {7, 0});
        board.board[7][7] = new Rook("W", new int[] {7, 7});
        board.board[7][4] = new King("W", new int[] {7, 4});
        board.printBoard();     // try to move white king to the left
        Assertions.assertTrue(board.board[7][4].move(board, new int[] {7, 2}));
        board.printBoard();
    }

    @Test
    void testBlackCastlingLeft() {
        Board board = new Board(false);
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("B", new int[]{1, i}); // white pawns
        }

        board.board[0][0] = new Rook("B", new int[] {0, 0});
        board.board[0][7] = new Rook("B", new int[] {0, 7});
        board.board[0][4] = new King("B", new int[] {0, 4});
        board.printBoard();     // try to move black king to the left
        Assertions.assertTrue(board.board[0][4].move(board, new int[] {0, 2}));
        board.printBoard();
    }

    @Test
    void testBlackCastlingLeft2() {
        Board board = new Board(false);
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("B", new int[]{1, i}); // white pawns
        }

        board.board[0][0] = new Rook("B", new int[] {0, 0});
        board.board[0][7] = new Rook("B", new int[] {0, 7});
        board.board[0][4] = new King("B", new int[] {0, 4});
        board.printBoard();     // try to move black king to the left
        Assertions.assertTrue(board.board[0][4].move(board, new int[] {0, 3}));
        board.printBoard();
        Assertions.assertTrue(board.board[0][3].move(board, new int[] {0, 2}));
        board.printBoard();
    }

    @Test
    void testBlackCastlingRight() {
        Board board = new Board(false);
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("B", new int[]{1, i}); // white pawns
        }

        board.board[0][0] = new Rook("B", new int[] {0, 0});
        board.board[0][7] = new Rook("B", new int[] {0, 7});
        board.board[0][4] = new King("B", new int[] {0, 4});
        board.printBoard();     // try to move black king to the right
        board.board[0][4].move(board, new int[] {0, 6});
        board.printBoard();
    }

    @Test
    void testForIllegalMoves() {
        Board board = new Board(false);
        board.initEmptyBoard();
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("B", new int[]{1, i}); // white pawns
        }

        board.board[0][0] = new Rook("B", new int[] {0, 0});
        board.board[0][7] = new Rook("B", new int[] {0, 7});
        board.board[0][4] = new King("B", new int[] {0, 4});
        board.board[0][5] = new Bishop("B", new int[] {0, 5});
        board.printBoard();     // test for move illegality
        Assertions.assertFalse(board.board[0][4].move(board, new int[] {0, 6})); // rook blocking the way
    }

    @Test
    void testKingUnsafeMove() {
        Board board = new Board(false);
        board.initEmptyBoard(); // make empty board
        // init pieces
        board.board[0][0] = new Rook("B", new int[] {0, 0});
        board.board[0][7] = new Rook("B", new int[] {0, 7});
        board.board[0][4] = new King("B", new int[] {0, 4});
        board.board[0][5] = new Bishop("B", new int[] {0, 5});
        board.board[1][4] = new Pawn("W", new int[] {1, 4});
        board.printBoard();
        // try to move king to an unsafe position
        Assertions.assertFalse(board.board[0][4].move(board, new int[] {0, 3}));
    }
}