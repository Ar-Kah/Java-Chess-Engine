package game;

public class Board {
    private long enPassantPawn = 0x0000000000000000L;
    private long whitePawns;
    private long whiteKnights;
    private long whiteBishops;
    private long whiteRooks;
    private long whiteQueens;
    private long whiteKing;
    private long blackPawns;
    private long blackKnights;
    private long blackBishops;
    private long blackRooks;
    private long blackQueens;
    private long blackKing;

    // Occupancy bitboards
    private long whitePieces;
    private long blackPieces;
    private long allPieces;

    public Board() {
        this.whitePawns = 0x000000000000FF00L;
        this.whiteKnights = 0x0000000000000042L;
        this.whiteBishops = 0x0000000000000024L;
        this.whiteRooks   = 0x0000000000000081L;
        this.whiteQueens  = 0x0000000000000008L;
        this.whiteKing    = 0x0000000000000010L;
        this.blackPawns = 0x00FF000000000000L;
        this.blackKnights = 0x4200000000000000L;
        this.blackBishops = 0x2400000000000000L;
        this.blackRooks   = 0x8100000000000000L;
        this.blackQueens  = 0x0800000000000000L;
        this.blackKing    = 0x1000000000000000L;
        updateOccupancy();
    }

    private void updateOccupancy() {
        whitePieces = whitePawns | whiteKnights | whiteBishops | whiteRooks | whiteQueens | whiteKing;
        blackPieces = blackPawns | blackKnights | blackBishops | blackRooks | blackQueens | blackKing;
        allPieces = whitePieces | blackPieces;
    }
    public void printChessboard() {
        char[][] board = new char[8][8];

        // Fill board with empty squares
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                board[rank][file] = '.';
            }
        }

        // Place pieces on the board
        placePieces(board, whitePawns, 'P');
        placePieces(board, whiteKnights, 'N');
        placePieces(board, whiteBishops, 'B');
        placePieces(board, whiteRooks, 'R');
        placePieces(board, whiteQueens, 'Q');
        placePieces(board, whiteKing, 'K');

        placePieces(board, blackPawns, 'p');
        placePieces(board, blackKnights, 'n');
        placePieces(board, blackBishops, 'b');
        placePieces(board, blackRooks, 'r');
        placePieces(board, blackQueens, 'q');
        placePieces(board, blackKing, 'k');

        // Print the board
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                System.out.print(board[rank][file] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Place pieces on the board based on bitboard
    private void placePieces(char[][] board, long bitboard, char piece) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                long square = 1L << (rank * 8 + file);
                if ((bitboard & square) != 0) {
                    board[rank][file] = piece;
                }
            }
        }
    }

    public static void printBitboard(long bitboard) {
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                System.out.print(((bitboard & (1L << square)) != 0) ? "1 " : ". ");
            }
            System.out.println();
        }
        System.out.println("\n--------------------\n");
    }

    public boolean movePiece(String move, boolean isWhite) {
        if (move.length() < 4) {
            System.out.println("Invalid input");
            return false;
        }

        int from = (move.charAt(1) - '1') * 8 + (move.charAt(0) - 'a');
        int to = (move.charAt(3) - '1') * 8 + (move.charAt(2) - 'a');

//        System.out.println(from + " " + to);
        long fromMask = 1L << from;
        long toMask = 1L << to;
        if ((allPieces & fromMask) == 0) {
            System.out.println("No piece in this location");
            return false;
        }

        if (isWhite && (whitePieces & fromMask) == 0) {
            System.out.println("No White piece in this location");
            return false;
        }
        if (!isWhite && ((blackPieces & fromMask) == 0)) {
            System.out.println("No black piece in this location");
            return false;
        }

        /*TODO there needs to be a checker when capturing the enPassant piece it needs to be deleted as well (13.3.2025)*/
        if ((whitePawns & fromMask) != 0) {
            // check move legality
            if ((Pawn.legalMovesWhite(fromMask, blackPieces, allPieces, enPassantPawn) & toMask) != 0) {
                whitePawns &= ~fromMask;    // delete the starting point of the pawn
                whitePawns |= toMask;       // move the pawn to the target position
                if (fromMask << 16 == toMask) {
                    enPassantPawn |= toMask;
                } else {
                    enPassantPawn = 0x0000000000000000L; // enPassant not active
                }
            } else {
                return false; // wasn't a legal move
            }
        } else if ((blackPawns & fromMask) != 0) {
            // check for move legality
            if ((Pawn.legalMovesBlack(fromMask, whitePieces, allPieces, enPassantPawn) & toMask) != 0) {
                blackPawns &= ~fromMask;
                blackPawns |= toMask;
                if (fromMask >> 16 == toMask) {
                    enPassantPawn |= toMask;
                } else {
                    enPassantPawn = 0x0000000000000000L; // mark enPassant as not active
                }
            } else {
                return false; // wasn't a legal move
            }
        }

        updateOccupancy();  // update new bitboards to all-, black- and whitePieces
        return true;
    }

}
