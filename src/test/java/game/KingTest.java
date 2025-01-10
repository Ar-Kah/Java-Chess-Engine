package game;

import org.junit.jupiter.api.Test;

class KingTest {
    @Test
    void testWhiteCastlingRight() {
        Board board = new Board();
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("W", new int[]{6, i}); // white pawns
        }

        board.board[7][0] = new Rook("W", new int[] {7, 0});
        board.board[7][7] = new Rook("W", new int[] {7, 7});
        board.board[7][4] = new King("W", new int[] {7, 4});
        board.printBoard();     // try to move white king to the right
        board.board[7][4].move(board, new int[] {7, 6});
    }

    @Test
    void testWhiteCastlingLeft() {
        Board board = new Board();
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("W", new int[]{6, i}); // white pawns
        }

        board.board[7][0] = new Rook("W", new int[] {7, 0});
        board.board[7][7] = new Rook("W", new int[] {7, 7});
        board.board[7][4] = new King("W", new int[] {7, 4});
        board.printBoard();     // try to move white king to the left
        board.board[7][4].move(board, new int[] {7, 2});
    }

    @Test
    void testBlackCastlingLeft() {
        Board board = new Board();
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[1][i] = new Pawn("B", new int[]{1, i}); // white pawns
        }

        board.board[0][0] = new Rook("B", new int[] {0, 0});
        board.board[0][7] = new Rook("B", new int[] {0, 7});
        board.board[0][4] = new King("B", new int[] {0, 4});
        board.printBoard();     // try to move black king to the left
        board.board[0][4].move(board, new int[] {0, 2});
    }

    @Test
    void testBlackCastlingRight() {
        Board board = new Board();
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
    }
}