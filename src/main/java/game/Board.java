package game;


public class Board {

    public ChessPiece[][] board;

    public Board() {
        this.board = new ChessPiece[8][8];
        initBlack();
        initSpace();
        initWhite();
    }

    private void initSpace() {
        for (int column = 2; column < 7; column++) {
            for (int row = 0; row < 8; row++) {
                this.board[column][row] = new Space(new int[] {column, row});
            }
        }
    }

    public void printBoard() {

        String[] alphabets = {"a", "b", "c", "d", "e", "f", "g", "h"};
        System.out.print("  ");
        for (String character: alphabets) {
            System.out.print(character + " ");
        }
        System.out.println();
        int number = 8;
        // Example: Print the matrix
        for (ChessPiece[] row : this.board) {
            System.out.print(number + " ");
            for (ChessPiece cell : row) {
                System.out.print(cell.character + " ");
            }
            number--;
            System.out.println();
        }
    }

    private void initBlack() {
        this.board[0][0] = new Rook("B", new int[] {0, 0});
        this.board[0][1] = new Knight("B", new int[]{0, 1});
        this.board[0][2] = new Bishop("B", new int[]{0, 2});
        this.board[0][3] = new Queen("B", new int[]{0, 3});
        this.board[0][4] = new King("B", new int[]{0, 4});
        this.board[0][5] = new Bishop("B", new int[]{0, 5});
        this.board[0][6] = new Knight("B", new int[]{0, 6});
        this.board[0][7] = new Rook("B", new int[]{0, 7});
        // initialize pawns
        for (int i = 0; i < 8; i++) {
            this.board[1][i] = new Pawn("B", new int[]{1, i});
        }
    }

    private void initWhite() {
        // initialize pawns
        for (int i = 0; i < 8; i++) {
            this.board[6][i] = new Pawn("W", new int[]{6, i});
        }
        // rest
        this.board[7][0] = new Rook("W", new int[] {7, 0});
        this.board[7][1] = new Knight("W", new int[]{7, 1});
        this.board[7][2] = new Bishop("W", new int[]{7, 2});
        this.board[7][3] = new Queen("W", new int[]{7, 3});
        this.board[7][4] = new King("W", new int[]{7, 4});
        this.board[7][5] = new Bishop("W", new int[]{7, 5});
        this.board[7][6] = new Knight("W", new int[]{7, 6});
        this.board[7][7] = new Rook("W", new int[]{7, 7});
    }
}
