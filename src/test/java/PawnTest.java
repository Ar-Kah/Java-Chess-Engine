import game.Board;
import game.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PawnTest {
    @Test
    void pawnLegalMoves() {
        long allPiece = 0xFFFF000000000000L;
        long otherPiece = 0x0000000000280000L;
        long pawn  = 0x0000000000100000L;
        Board.printBitboard(allPiece);
        Board.printBitboard(pawn);
        Pawn.legalMoves(pawn, otherPiece, allPiece);

    }
}
