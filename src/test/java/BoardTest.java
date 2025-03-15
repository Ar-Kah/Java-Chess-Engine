import game.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class BoardTest {

    @Test
    void testAllMoveGeneration() {
        Board board = new Board();
        board.generateAllMoves(true);
    }
}
