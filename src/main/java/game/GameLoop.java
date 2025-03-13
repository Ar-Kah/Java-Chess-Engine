package game;

import java.util.Scanner;

public class GameLoop {
    private Board board;
    private boolean isWhite = true;

    public GameLoop(Board board) {
        this.board = board;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.printChessboard();
            String turn = isWhite ? "White's" : "Black's";
            System.out.println(turn + " turn");

            boolean isGood = false;
            while (!isGood) {
                System.out.print("Enter move (e.g., e2e4 or 'exit' to quit): ");
                String move = scanner.nextLine().trim();

                if (move.equalsIgnoreCase("exit")) {
                    System.out.println("Game over.");
                    scanner.close();
                    return; // Exit the game loop
                }

                isGood = board.movePiece(move, isWhite);
                if (!isGood) {
                    System.out.println("Invalid move. Try again.");
                }
            }

            isWhite = !isWhite; // Only switch turn if the move was valid
        }
    }
}
