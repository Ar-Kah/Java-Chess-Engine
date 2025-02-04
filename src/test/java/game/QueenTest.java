package game;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {
    @Test
    void testQueenMovement() {
        Board board = new Board(false);
        board.initEmptyBoard();
        board.board[3][4] = new Queen("B", new int[]{3, 4});
        Queen queen = (Queen) board.board[3][4];
        List<int[]> moves = board.board[3][4].getMoves(board);
        board.printBoard();
        int[] oldPosition = queen.position;;
        for (int[] move: moves) {

        }
    }
}