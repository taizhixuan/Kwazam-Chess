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
            System.out.println("Current player: " + controller.getCurrentPlayer());
            System.out.println("Enter your move (format: fromRow fromCol toRow toCol):");

            try {
                int fromRow = scanner.nextInt();
                int fromCol = scanner.nextInt();
                int toRow = scanner.nextInt();
                int toCol = scanner.nextInt();

                Position from = new Position(fromRow, fromCol);
                Position to = new Position(toRow, toCol);

                if (controller.movePiece(from, to)) {
                    System.out.println("Move successful.");
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Must be four integers.");
                scanner.nextLine(); // clear buffer
            }
        }

        System.out.println("Game over! The winner is " + controller.getCurrentPlayer());
        scanner.close();
    }

    private void displayBoard() {
        Board board = controller.getBoard();
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getColumns(); c++) {
                Piece piece = board.getPieceAt(new Position(r, c));
                if (piece == null) {
                    System.out.print(" . ");
                } else {
                    String colorPrefix = (piece.getColor() == Color.RED) ? "R" : "B";
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
