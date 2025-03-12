package GUI;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 85;
    int cols = 8;
    int rows = 8;

    Input input = new Input(this);
    public Piece selectedPiece;
    ArrayList<Piece> pieceList = new ArrayList<Piece>();

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPieces();
    }

    public void addPieces() {
        // black side
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 1, false));
        }
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4,0, false));

        // white side
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 6, true));
        }
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4,7, true));
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // paint the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c+r)%2==0 ? new Color(79, 54, 29) : new Color(180, 165, 98));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }
        // paint valid move highlights
        if (selectedPiece != null) {
            for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isValidMove(new Move(this, selectedPiece, c, r))) {
                    g2d.setColor(new Color(58, 190, 31, 106));
                    g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                }
            }
        }
            }
        for (Piece piece: pieceList) {
            piece.paint(g2d);
        }
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece: pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidMove(Move move) {
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }

        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null ||p2 == null) return false;
        return p1.isWhite == p2.isWhite;
    }

    public void makeMove(Move move) {
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;

        // change boolean if first move
        move.piece.isFirstMove = false;

        capture(move);
    }

    public void capture(Move move) {
        pieceList.remove(move.capture);
    }
}
