import game.Board;
import game.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PawnTest {
    @Test
    void pawnLegalMoves() {
        long enPassant = 0x0000002000000000L;
        long allPiece = 0xFFFF000000000000L;
        long otherPiece = 0x0000000000280000L;
        long pawn  = 0x0000001000000000L;
//        Board.printBitboard(enPassant);
//        Board.printBitboard(pawn);
        long moves = Pawn.legalMovesWhite(pawn, otherPiece, allPiece, enPassant);
        Board.printBitboard(moves);
    }
}
