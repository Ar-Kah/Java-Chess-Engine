package game;

import org.junit.jupiter.api.Test;
import java.util.List;
import org.junit.jupiter.api.Assertions;

class QueenTest {
    @Test
    void testQueenMovement() {
        Board board = new Board(false);
        board.initEmptyBoard();
        board.board[3][4] = new Queen("B", new int[]{3, 4});
        Queen queen = (Queen) board.board[3][4];
        List<int[]> moves = board.board[3][4].getMoves(board);
        for (int[] move: moves) {
            Board clone = Board.clone(board);
            Queen queen1 = (Queen) clone.board[3][4];
            Assertions.assertTrue(queen1.move(clone, move));
        }
    }
}