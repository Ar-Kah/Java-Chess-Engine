package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    public static final int WHITE = 1;
    public static final int BLACK = 0;
    private static final char EMPTY = '.';

    private final ArrayList<Piece> board;

    public Board() {
        board = new ArrayList<>(64);
        initializeBoard();
    }

    private void initializeBoard() {
        String initialSetup = "RNBQKBNRPPPPPPPP................................pppppppprnbqkbnr";
        for (int i = 0; i < 64; i++) {
            switch (initialSetup.charAt(i)) {
                case 'R': new Rook(BLACK, i);
                case 'N': new Knight(BLACK, i);
                case 'B': new Bishop(BLACK, i);
                case 'Q': new Queen(BLACK, i);
                case 'K': new King(BLACK, i);
                case 'P': new Pawn(BLACK, i);
                case 'p': new Pawn(WHITE, i);
                case 'r': new Rook(WHITE, i);
                case 'n': new Knight(WHITE, i);
                case 'b': new Bishop(WHITE, i);
                case 'q': new Queen(WHITE, i);
                case 'k': new King(WHITE, i);
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                System.out.print(board[row * 8 + col] + " ");
            }
            System.out.println();
        }
    }

}
