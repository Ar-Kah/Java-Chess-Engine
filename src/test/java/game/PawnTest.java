package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void isCheckingKing() {
    }

    @Test
    void canMoveTo() {
        Pawn pawn = new Pawn("W", new int[] {6, 0});
        Board board = new Board();
        pawn.move(board, new int[] {4, 0});
    }
}