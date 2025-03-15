package game;


import java.util.List;

public class Board {

    public ChessPiece[][] board;
    private boolean check = false;
    private boolean checkMate = false;
    private boolean staleMate = false;
    private ChessPiece checkingPiece = null;
    public ChessPiece enPassant = null;
    public int turnsAfterEnPassant = 0;
    public boolean enPassantActive = false;
    public Board(boolean init) {
        this.board = new ChessPiece[8][8];
        if (init) {
            initBlack();
            initSpace();
            initWhite();
        }
    }

    private void initSpace() {
        for (int column = 2; column < 7; column++) {
            for (int row = 0; row < 8; row++) {
                this.board[column][row] = new Space(new int[] {column, row});
            }
        }
    }

    // Initialize an empty board
    public void initEmptyBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                this.board[row][col] = new Space(new int[]{row, col});
            }
        }
    }

    public void printBoard() {
        System.out.print("  |----------------|\n");
        int number = 8;
        // Example: Print the matrix
        for (ChessPiece[] row : this.board) {
            System.out.print(number + " |");
            for (ChessPiece cell : row) {
                System.out.print(cell.character + " ");
            }
            number--;
            System.out.println("|");
        }
        String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H"};
        System.out.print("  |----------------|\n");
        System.out.print("   ");
        for (String character: alphabets) {
            System.out.print(character + " ");
        }
        System.out.println();
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


    public static Board clone(Board board) {
        Board clonedBoard = new Board(false);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece toBeCloned = board.board[i][j];
                clonedBoard.board[i][j] = toBeCloned.clone();
            }
        }

        clonedBoard.setStaleMate(board.staleMate);
        clonedBoard.setCheckMate(board.checkMate);
        clonedBoard.setEnPassant(board.enPassant);
        clonedBoard.setStaleMate(board.staleMate);
        clonedBoard.setCheck(board.check);

        return clonedBoard;
    }

    public boolean checkForCheckmate(String color) {
        King king = ChessPiece.findKing(color, this);
        if (king == null) return false; // Should never happen in a valid game

        if (!king.isUnderAttack(this)) return false; // King is not in check, so no checkmate

        // Check if any move can save the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    List<int[]> moves = piece.getMoves(this);
                    for (int[] move : moves) {
                        if (!piece.isKingCheckedAfterMove(this, move)) {
                            return false; // There exists a legal move to escape check
                        }
                    }
                }
            }
        }

        return true; // No legal moves left, and king is in check → Checkmate
    }



    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setCheckingPiece(ChessPiece checkingPiece) {
        this.checkingPiece = checkingPiece;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public boolean isStaleMate() {
        return staleMate;
    }

    public void setStaleMate(boolean staleMate) {
        this.staleMate = staleMate;
    }

    public ChessPiece getCheckingPiece() {
        return checkingPiece;
    }

    public void setEnPassant(ChessPiece enPassant) {
        this.enPassant = enPassant;
    }
}
