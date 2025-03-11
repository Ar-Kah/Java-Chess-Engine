package GUI;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();

    // colors
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor =  WHITE;

    // pieces
    public static ArrayList<Piece> pieces = new ArrayList<Piece>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        setPieces();
        copyPieces(pieces, simPieces);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lasttime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lasttime)/drawInterval;
            lasttime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        target.addAll(source);
    }

    public void setPieces() {
        // White side
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(WHITE, i, 6));
        }
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Bishop(WHITE, 2, 7));

        // black side
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(BLACK, i, 1));
        }
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
    }

    private void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);

        // draw pieces
        for (Piece piece: simPieces) {
            piece.draw(g2);
        }
    }

}
