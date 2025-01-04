package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {
    @Test
    void testCastling() {
        Board board = new Board();
        board.initEmptyBoard();
        // test white king castling
        for (int i = 0; i < 8; i++) {
            board.board[6][i] = new Pawn("W", new int[]{6, i}); // white pawns
        }

        board.board[7][0] = new Rook("W", new int[] {7, 0});
        board.board[7][7] = new Rook("W", new int[] {7, 7});
        board.board[7][4] = new King("W", new int[] {7, 4});
        board.printBoard();     // try to move king
        board.board[7][4].move(board, new int[] {7, 6});
    }
}