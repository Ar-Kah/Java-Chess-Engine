package org.example;

import java.lang.Math;
import java.util.Arrays;
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
                // parse input
                String[] inputInArray = input.split(" ");
                String coordOfMovedPiece = inputInArray[0];
                String[] movedPiececoords = coordOfMovedPiece.split("");
                String coords = inputInArray[1];
                String[] coordsWherePieceWillBeMoved = coords.split("");

                // change the row from char to int
                int columnOfMovedPiece = stringToInteger(movedPiececoords[0]);
                // change the column index from the game view to array index using absolute value
                int rowOfMovedPiece = Math.abs(Integer.parseInt(movedPiececoords[1]) - 8);

                System.out.println(columnOfMovedPiece + " " + rowOfMovedPiece);
                ChessPiece piece = board.board[rowOfMovedPiece][columnOfMovedPiece];

                // check if the chosen coordinate is an empty space
                if (piece instanceof Space) {
                    System.out.println("There is no chess piece in this location");
                    continue;
                }

                if (!isWhite && piece.color.equals("W")) {
                    System.out.println("It is blacks turn to move");
                    continue;
                } else if (isWhite && piece.color.equals("B")) {
                    System.out.println("It is whites turn to move");
                }

                System.out.println(piece.name+" "+ Arrays.toString(piece.position));
                piece.move(board, new int[]{stringToInteger(coordsWherePieceWillBeMoved[0]),
                        Math.abs(Integer.parseInt(coordsWherePieceWillBeMoved[1]) - 8)});

                isWhite = !isWhite;

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.toString());
            }
        }
    }

    private int stringToInteger(String character) {
        return CHARTOINT.get(character);
    }
}
