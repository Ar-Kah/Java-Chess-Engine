package org.example;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameLogic {
    final Map<String, Integer> CHARTOINT = new HashMap<>()
    {
        {
            put("a", 0);
            put("b", 1);
            put("c", 2);
            put("d", 3);
            put("e", 4);
            put("f", 5);
            put("g", 6);
            put("h", 7);
        }
    };
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

            try {
                String input = scanner.nextLine();
                input = input.toLowerCase();

                // quit the game loop when given a quit command
                if (input.equals("q") || input.equals("quit")) {
                    break;
                }
                // compensate the index number by retracting 1
                String[] values = input.split(" ");
                int pieceRow = Integer.parseInt(values[1]); // parse row to opposite index
                pieceRow = Math.abs(pieceRow - 8);
                String pieveColumn = values[0];
                int pieveColumnInt = stringToInteger(pieveColumn);
                int wantedMoveRow = Integer.parseInt(values[3]); // parse row to opposite index
                wantedMoveRow = Math.abs(wantedMoveRow - 8);
                String wantedMoveColumn = values[2];
                int wantedMoveColumnInt = stringToInteger(wantedMoveColumn);
                int[] movingPosition = {wantedMoveRow, wantedMoveColumnInt};
                // Find the chess piece from the position from the input
                ChessPiece piece = board.board[pieceRow][pieveColumnInt];

                piece.move(board, movingPosition);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    private int stringToInteger(String character) {
        return CHARTOINT.get(character);
    }
}
