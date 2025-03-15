package game;

import game.bot.MinMax;

import java.lang.Math;
import java.util.*;

public class RunGame {
    /*
    this hashmap is used to change the input value from a character
    to an integer for indexing the 2d Array of the game board
     */
    private final Map<String, Integer> CHARTOINT = new HashMap<>()
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
    private final boolean playWithBot = true;
    private boolean gameOver = false;
    private int totalMoves = 1;
    private boolean isWhite = true;     // oscillator for turns
    private final Board board;
    public RunGame(Board board) {
        this.board = board;
    }

    /**
     * In this method we take the input of the players and convert the input to a format our program understands.
     * Then we try moving the pieces and check for validity.
     */
    public void run() {
        while (true) {
            if (checkForStaleMate()) {
                System.out.println("Game ended in a stalemate.");
                gameOver = true;
                return;
            }

            /*if (checkForCheckMate()) {
                String winner = isWhite ? "Black" : "White";
                System.out.println(winner + " has won by checkmate!");
                board.setCheckMate(true);
                gameOver = true;
                return;
            }*/


            String color = isWhite ? "Whites" : "Blacks";
            if (board.isCheck()) {
                System.out.println(color + " king is checked");
            }

            Scanner scanner = new Scanner(System.in);
            if (isWhite) {
                System.out.println("Whites turn to move: ");

            } else if(playWithBot) {
                System.out.println("Blacks turn to move: ");

                MinMax minMax = new MinMax(board);
                board.printBoard();
                isWhite = true;
                totalMoves++;
                continue;
            } else {
                System.out.println("Blacks turn to move: ");
            }

            try {

                String input = scanner.nextLine();
                input = input.toLowerCase();

                // quit the game loop when given a quit command
                if (input.equals("q") || input.equals("quit")) return;
                // parse input
                String[] inputInArray = input.split(" ");
                String coordOfMovedPiece = inputInArray[0];
                String[] movedPieceCoordsSplit = coordOfMovedPiece.split("");
                String coords = inputInArray[1];
                String[] coordsWherePieceWillBeMoved = coords.split("");

                // change the row from char to int
                int columnOfMovedPiece = stringToInteger(movedPieceCoordsSplit[0]);
                // change the column index from the game view in the terminal to array indexing using absolute values
                int rowOfMovedPiece = Math.abs(Integer.parseInt(movedPieceCoordsSplit[1]) - 8);

                ChessPiece movedPiece = board.board[rowOfMovedPiece][columnOfMovedPiece]; // try to declare

                // check if the chosen coordinate is an empty space
                if (movedPiece instanceof Space) {
                    System.out.println("There is no chess piece in this location");
                    continue;
                }

                // check for correct color
                if (!isWhite && movedPiece.color.equals("W")) {
                    System.out.println("It is blacks turn to move");
                    continue;
                } else if (isWhite && movedPiece.color.equals("B")) {
                    System.out.println("It is whites turn to move");
                    continue;
                }

                int column = stringToInteger(coordsWherePieceWillBeMoved[0]);               // change the row from character to integer
                int row = Math.abs(Integer.parseInt(coordsWherePieceWillBeMoved[1]) - 8);   // here we repeat the indexing thing we did on line 65
                boolean isValid = movedPiece.move(board, new int[] {row, column});          // try moving the piece

                if (!isValid) {
                    continue;           // invalid move
                }

                // Handle enPassant rules after a move
                if (board.enPassantActive) {
                    if (board.enPassant.color.equals(isWhite ? "B" : "W")) {
                        // Opponent made a move without capturing en passant; reset
                        board.enPassantActive = false;
                        board.enPassant = null;
                    }
                }

                board.printBoard();

                isWhite = !isWhite;     // change side
                totalMoves++;           // add a move

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                System.out.println("Invalid input! Try again.");
                run();
            }
        }
    }

    private int stringToInteger(String character) {
        return CHARTOINT.get(character);
    }

    /**
     * method for checking for checkmate in the game
     * @return true if checkmate
     */
    public boolean checkForCheckmate() {

        // can't continue without game being a check situation
        if (!board.isCheck()) {
            return false;
        }

        String color = isWhite ? "W" : "B";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                ChessPiece piece = board.board[1][j];
                // skip same colored pieces. This includes spaces.
                if (color.equals(piece.color)) continue;
                List<int[]> moves = new ArrayList<>();
                moves = piece.getMoves(board);
                if (moves == null) continue;

                // get the opponents king
                String opponentsColor;
                if (color.equals("W")) opponentsColor = "B";
                else opponentsColor = "W";

                // try all moves to remove the check and if this is not possible declare checkmate
                for (int[] move : moves) {
                    // clone the board to check if move removes check
                    Board clonedBoard = Board.clone(board);
                    piece.move(clonedBoard, move);
                    King opponentsKing = Board.findKing(opponentsColor, clonedBoard);
                    assert opponentsKing != null;
                    if (!opponentsKing.isUnderAttack(clonedBoard)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkForStaleMate() {
        // start by getting the color of the side trying to move and parse it to string format
        String color = isWhite ? "W" : "B";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // start checking for legal moves
                ChessPiece piece = board.board[i][j];
                if (!color.equals(piece.color)) continue; // skip different colored pieces which include empty spaces

                List<int[]> moves = piece.getMoves(board);
                // if we have even one legal move then there is no stalemate
                if (!moves.isEmpty()) return false;
            }
        }

        board.setStaleMate(true);
        return true;
    }
}
