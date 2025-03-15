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

    public boolean formatInput(String move, boolean isWhite) {
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

        /*TODO there needs to be a checker when capturing
           the enPassant piece it needs to be deleted as well (13.3.2025)*/

        /*TODO I need to figure out how to iterate all of the moves for a future minimax algo (14.3.2025)*/
        if ((whitePawns & fromMask) != 0) {
            // check move legality
            if ((Pawn.legalMovesWhite(fromMask, blackPieces, allPieces, enPassantPawn) & toMask) != 0) {
                whitePawns &= ~fromMask;    // delete the starting point of the pawn
                whitePawns |= toMask;       // move the pawn to the target position
                removeCapturedPiece(toMask, isWhite);
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
                removeCapturedPiece(toMask, isWhite);
                if (fromMask >> 16 == toMask) {
                    enPassantPawn |= toMask;
                } else {
                    enPassantPawn = 0x0000000000000000L; // mark enPassant as not active
                }
            } else {
                return false; // wasn't a legal move
            }
        } else if (isWhite && (whiteKnights & fromMask) != 0) {
            if ((Knight.legalMoves(fromMask, blackPieces, allPieces) & toMask) != 0) {
                whiteKnights &= ~fromMask;
                whiteKnights |= toMask;
                enPassantPawn = 0x0000000000000000L;
                removeCapturedPiece(toMask, true);
            } else {
                return false;
            }
        } else if (!isWhite && (blackKnights & fromMask) != 0) {
            if ((Knight.legalMoves(fromMask, whitePieces, allPieces) & toMask) != 0) {
                blackKnights &= ~fromMask;
                blackKnights |= toMask;
                enPassantPawn = 0x0000000000000000L;
                removeCapturedPiece(toMask, false);
            } else {
                return false;
            }
        }

        updateOccupancy();  // update new bitboards to all-, black- and whitePieces
        return true;
    }
    private void removeCapturedPiece(long toMask, boolean isWhite) {
        if (!isWhite) {
            if ((whitePawns & toMask) != 0) whitePawns &= ~toMask;
            else if ((whiteKnights & toMask) != 0) whiteKnights &= ~toMask;
            else if ((whiteBishops & toMask) != 0) whiteBishops &= ~toMask;
            else if ((whiteRooks & toMask) != 0) whiteRooks &= ~toMask;
            else if ((whiteQueens & toMask) != 0) whiteQueens &= ~toMask;
            else if ((whiteKing & toMask) != 0) whiteKing &= ~toMask;
        } else {
            if ((blackPawns & toMask) != 0) blackPawns &= ~toMask;
            else if ((blackKnights & toMask) != 0) blackKnights &= ~toMask;
            else if ((blackBishops & toMask) != 0) blackBishops &= ~toMask;
            else if ((blackRooks & toMask) != 0) blackRooks &= ~toMask;
            else if ((blackQueens & toMask) != 0) blackQueens &= ~toMask;
            else if ((blackKing & toMask) != 0) blackKing &= ~toMask;
        }
    }

    public void generateAllMoves(boolean isWhite) {
        long myPawns = isWhite ? whitePawns : blackPawns;
        long myKnights = isWhite ? whiteKnights : blackKnights;
        long myBishops = isWhite ? whiteBishops : blackBishops;
        long myRooks = isWhite ? whiteRooks : blackRooks;
        long myQueens = isWhite ? whiteQueens : blackQueens;
        long myKing = isWhite ? whiteKing : blackKing;

        // Iterate over all pieces and generate their legal moves
        iteratePieceMoves(myPawns, "Pawn", isWhite);
        iteratePieceMoves(myKnights, "Knight", isWhite);
        iteratePieceMoves(myBishops, "Bishop", isWhite);
        iteratePieceMoves(myRooks, "Rook", isWhite);
        iteratePieceMoves(myQueens, "Queen", isWhite);
        iteratePieceMoves(myKing, "King", isWhite);
    }

    private void iteratePieceMoves(long pieceBitboard, String pieceType, boolean isWhite) {
        while (pieceBitboard != 0) {
            int fromSquare = Long.numberOfTrailingZeros(pieceBitboard); // Get LSB index
            long fromMask = 1L << fromSquare;  // Convert to bit mask

            long possibleMoves = getMovesForPiece(pieceType, fromMask, isWhite); // Get move bitboard

            // Iterate over possible moves
            while (possibleMoves != 0) {
                int toSquare = Long.numberOfTrailingZeros(possibleMoves);
                long toMask = 1L << toSquare;

                System.out.println(pieceType + " moves from " + squareToAlgebraic(fromSquare) +
                        " to " + squareToAlgebraic(toSquare));

                possibleMoves &= possibleMoves - 1; // Remove processed move
            }

            pieceBitboard &= pieceBitboard - 1; // Remove processed piece
        }
    }

    // Convert bitboard index to algebraic notation
    private String squareToAlgebraic(int square) {
        int file = square % 8;
        int rank = square / 8;
        return (char) ('a' + file) + Integer.toString(rank + 1);
    }

    private long getMovesForPiece(String pieceType, long fromMask, boolean isWhite) {
        switch (pieceType) {
            case "Pawn":
                return isWhite ? Pawn.legalMovesWhite(fromMask, blackPieces, allPieces, enPassantPawn)
                        : Pawn.legalMovesBlack(fromMask, whitePieces, allPieces, enPassantPawn);
            case "Knight":
                return Knight.legalMoves(fromMask, isWhite ? blackPieces : whitePieces, allPieces);
            // Add bishop, rook, queen, king move generation...
        }
        return 0L;
    }

}
