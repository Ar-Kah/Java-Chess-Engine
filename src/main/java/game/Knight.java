package game;

public class Knight {

    // File masks to prevent wraparounds
    private static final long NOT_A_FILE = 0xfefefefefefefefeL; // Clears file A
    private static final long NOT_AB_FILE = 0xfcfcfcfcfcfcfcfcL; // Clears files A & B
    private static final long NOT_H_FILE = 0x7f7f7f7f7f7f7f7fL; // Clears file H
    private static final long NOT_GH_FILE = 0x3f3f3f3f3f3f3f3fL; // Clears files G & H

    public static long legalMoves(long knight, long opponentsPieces, long allPieces) {
        long legalmoves = 0L;

        // Up 2, Right 1 (Only if not on column H)
        if ((knight & NOT_H_FILE) != 0) legalmoves |= (knight << 17) & (~allPieces | opponentsPieces);
        // Up 2, Left 1 (Only if not on column A)
        if ((knight & NOT_A_FILE) != 0) legalmoves |= (knight << 15) & (~allPieces | opponentsPieces);
        // Up 1, Right 2 (Only if not on column G or H)
        if ((knight & NOT_GH_FILE) != 0) legalmoves |= (knight << 10) & (~allPieces | opponentsPieces);
        // Up 1, Left 2 (Only if not on column A or B)
        if ((knight & NOT_AB_FILE) != 0) legalmoves |= (knight << 6) & (~allPieces | opponentsPieces);
        // Down 1, Right 2 (Only if not on column G or H)
        if ((knight & NOT_GH_FILE) != 0) legalmoves |= (knight >> 6) & (~allPieces | opponentsPieces);
        // Down 1, Left 2 (Only if not on column A or B)
        if ((knight & NOT_AB_FILE) != 0) legalmoves |= (knight >> 10) & (~allPieces | opponentsPieces);
        // Down 2, Right 1 (Only if not on column H)
        if ((knight & NOT_H_FILE) != 0) legalmoves |= (knight >> 15) & (~allPieces | opponentsPieces);
        // Down 2, Left 1 (Only if not on column A)
        if ((knight & NOT_A_FILE) != 0) legalmoves |= (knight >> 17) & (~allPieces | opponentsPieces);
        Board.printBitboard(legalmoves);
        return legalmoves;
    }
}
