package game;

public class Pawn {

    public static boolean legalMoves(long pawn, long blackPieces, long allPieces) {
        // one square
        long moveForward = pawn << 8;
        long moveForward2 = pawn << 16;
        long captureLeft = pawn << 7;
        long captureRight = pawn << 9;

        long row2 = 0x000000000000FF00L;

        long legalmoves = 0x0000000000000000L;
        // check if there are no piece in the way of moving forward with pawn
        if ((moveForward & allPieces) == 0) {
            if ((pawn & row2) != 0 && (moveForward2 & allPieces) == 0) {
                legalmoves |= moveForward2;
            }
            legalmoves |= moveForward;
        }
        if ((captureRight & blackPieces) != 0) {
            legalmoves |= captureRight;
        }
        if ((captureLeft & blackPieces) != 0) {
            legalmoves |= captureLeft;
        }
        Board.printBitboard(legalmoves);


        return false;
    }

}
