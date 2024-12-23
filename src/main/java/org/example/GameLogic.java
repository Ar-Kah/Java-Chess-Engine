package org.example;

import java.util.Scanner;

public class GameLogic {
    boolean isWhite = true;
    private final Board board;
    public GameLogic(Board board) {
        this.board = board;
    }

    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (isWhite) {
                System.out.println("Whites turn to move: ");
            } else {
                System.out.println("Blacks turn to move");
            }

            System.out.println("First move: ");

            try {
                String input = scanner.nextLine();
                input = input.toLowerCase();

                // quit the game loop when given a quit command
                if (input.equals("q") || input.equals("quit")) {
                    break;
                }

                String[] values = input.split(" ");
                int x = Integer.parseInt(values[0]);
                int y = Integer.parseInt(values[1]);
                int z = Integer.parseInt(values[2]);
                int k = Integer.parseInt(values[3]);
                int[] movingPosition = {z, k};
                // Find the chess piece from the position from the input
                ChessPiece piece = board.board[x][y];

                piece.move(board, movingPosition);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }
    }
}
