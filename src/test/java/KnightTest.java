import game.Board;
import game.Knight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class KnightTest {
    @Test
    void testKnightLegalMoves() {
        long knight = 0x400000000000000L;
        long allPiece = 0x0000000000000000L;
        long opponentsPieces = 0x0000000000000000L;
        Board.printBitboard(knight);
        Board.printBitboard(Knight.legalMoves(knight, opponentsPieces, allPiece));
    }
}
