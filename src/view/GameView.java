// GameView.java
package view;

import controller.GameController;
import model.Board;
import model.Color;
import model.Piece;
import model.Position;

import java.util.Scanner;

/**
 * A console-based view to test moves via text input.
 */
public class GameView {
    private final GameController controller;

    public GameView(GameController controller) {
        this.controller = controller;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Kwazam Chess!");

        while (!controller.isGameOver()) {
            displayBoard();
            String currentPlayer = controller.getCurrentPlayer();
            System.out.println("Current player: " + currentPlayer);
            System.out.println("Enter your move (format: fromRow fromCol toRow toCol):");

            try {
                int fromRow = scanner.nextInt();
                int fromCol = scanner.nextInt();
                int toRow   = scanner.nextInt();
                int toCol   = scanner.nextInt();

                // --- Coordinate Flip Only if RED is current player ---
//                if (currentPlayer.equals("RED")) {
//                    fromRow = 7 - fromRow; // 7 = last row index
//                    fromCol = 4 - fromCol; // 4 = last col index
//                    toRow   = 7 - toRow;
//                    toCol   = 4 - toCol;
//                }

                Position from = new Position(fromRow, fromCol);
                Position to   = new Position(toRow, toCol);

                // Attempt the move
                if (controller.movePiece(from, to)) {
                    System.out.printf(
                            "%s moved from (%d, %d) to (%d, %d) successfully.%n",
                            currentPlayer,
                            // Show the user the original typed values,
                            // which match the visual board labeling:
                            currentPlayer.equals("RED") ? (7 - fromRow) : fromRow,
                            currentPlayer.equals("RED") ? (4 - fromCol) : fromCol,
                            currentPlayer.equals("RED") ? (7 - toRow) : toRow,
                            currentPlayer.equals("RED") ? (4 - toCol) : toCol
                    );
                } else {
                    System.out.printf(
                            "Invalid move by %s. Try again.%n", currentPlayer
                    );
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Must be four integers (e.g., '0 1 0 2').");
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Once the game is over, announce the winner
        System.out.println("Game over! The winner is " + controller.getCurrentPlayer());
        scanner.close();
    }


    private void displayBoard() {
        Board board = controller.getBoard();
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getColumns(); c++) {
                Piece piece = board.getPieceAt(new Position(r, c));
                if (piece == null) {
                    // Empty square
                    System.out.print(" . ");
                } else {
                    // For example: "RB" for Red Bishop, "BK" for Blue King, etc.
                    String colorPrefix = (piece.getColor() == Color.RED) ? "R" : "B";
                    // Grab first 2 chars of the piece's class name (or as many as are available)
                    String pieceType = piece.getClass().getSimpleName().substring(0, 2);
                    System.out.print(" " + colorPrefix + pieceType + " ");
                }
            }
            System.out.println();
        }
    }

    private void saveGame(String filename) {
        try {
            controller.saveGameAsText(filename);
            System.out.println("Game saved successfully to " + filename);
        } catch (Exception e) {
            System.out.println("Failed to save the game: " + e.getMessage());
        }
    }

    private void loadGame(String filename) {
        try {
            controller.loadGame(filename);
            System.out.println("Game loaded successfully from " + filename);
        } catch (Exception e) {
            System.out.println("Failed to load the game: " + e.getMessage());
        }
    }
}

