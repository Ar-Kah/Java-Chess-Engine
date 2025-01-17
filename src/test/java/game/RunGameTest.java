package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RunGameTest {
    @Test
    void testForStaleMate() {
        Board board = new Board();
        board.initEmptyBoard();

        // make a stalemate situation
        board.board[0][0] = new King("W", new int[] {0, 0});
        board.board[2][1] = new King("B", new int[] {2, 1});
        board.board[2][3] = new Queen("B", new int[] {2, 3});
        board.printBoard();
        RunGame game = new RunGame(board);
        Assertions.assertTrue(game.checkForStaleMate());
    }

    /**
     * Check if moving a peace to a location where your own
     * king would be checked is an invalid move aka. returns False.
     */
    @Test
    void testKingWouldBeChecked() {
        Board board = new Board();

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
}