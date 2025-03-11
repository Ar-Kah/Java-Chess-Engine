package GUI;

import pieces.Pawn;
import pieces.Rook;
import pieces.Knight;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 85;
    int cols = 8;
    int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<Piece>();

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        addPieces();
    }

    public void addPieces() {
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 1, false));
        }
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Rook(this, 4, 0, false));

        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 6, true));
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c+r)%2==0 ? new Color(79, 54, 29) : new Color(180, 165, 98));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        for (Piece piece: pieceList) {
            piece.paint(g2d);
        }
    }
}
