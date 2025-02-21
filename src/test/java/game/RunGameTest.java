package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RunGameTest {
    @Test
    void testForStaleMate() {
        Board board = new Board(false);
        board.initEmptyBoard();

        // make a stalemate situation
        board.board[0][0] = new King("W", new int[] {0, 0});
        board.board[2][1] = new King("B", new int[] {2, 1});
        board.board[2][3] = new Queen("B", new int[] {2, 3});
        board.printBoard();
        RunGame game = new RunGame(board);
        Assertions.assertTrue(game.checkForStaleMate());
    }

    @Test
    void testForCheckMate() {
        Board board = new Board(false);
        board.initEmptyBoard();

        // make a stalemate situation
        board.board[0][0] = new King("B", new int[] {0, 0});
        board.board[7][1] = new King("W", new int[] {7, 1});
        board.board[2][3] = new Queen("W", new int[] {2, 3});
        board.board[6][1] = new Rook("W", new int[] {6, 2});
        board.printBoard();
        board.board[2][3].move(board, new int[] {0, 1});
        board.printBoard();
        RunGame game = new RunGame(board);
        // Assertions.assertTrue(game.checkForCheckMate("B"));
    }

    /**
     * Check if moving a peace to a location where your own
     * king would be checked is an invalid move aka. returns False.
     */
    @Test
    void testKingWouldBeChecked() {
        Board board = new Board(true);

        ChessPiece piece = board.board[6][4];
        Assertions.assertTrue(piece.move(board, new int[] {4, 4}));

        ChessPiece piece2 = board.board[1][4];
        Assertions.assertTrue(piece2.move(board, new int[] {3, 4}));

        ChessPiece piece3 = board.board[6][0];
        Assertions.assertTrue(piece3.move(board, new int[] {4, 0}));

        ChessPiece piece4 = board.board[0][3];
        Assertions.assertTrue(piece4.move(board, new int[] {4, 7}));

        ChessPiece piece5 = board.board[6][5];
        Assertions.assertFalse(piece5.move(board, new int[] {5, 5}));
    }

    @Test
    void checkmateTest() {
        Board board = new Board(false);
        board.initEmptyBoard();

        board.board[0][5] = new Rook("B", new int[] {0, 5});
        board.board[1][5] = new Pawn("B", new int[] {1, 5});
        board.board[1][7] = new King("B", new int[] {1, 7});
        board.board[1][6] = new Pawn("B", new int[] {1, 6});

        board.board[1][4] = new Knight("W", new int[] {1, 4});
        board.board[3][7] = new Rook("W", new int[] {3, 7});
        board.board[7][7] = new King("W", new int[] {7, 7});
        board.printBoard();
        RunGame game = new RunGame(board);
        board.setCheck(true);
        Assertions.assertTrue(game.checkForCheckmate());
    }
}