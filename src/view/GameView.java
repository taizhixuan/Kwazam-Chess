package view;

import controller.GameController;
import model.Board;
import model.Color;
import model.Piece;
import model.Position;

import java.util.Scanner;

/**
 * GameView provides a console-based interface for testing game moves via text input.
 * It interacts with the GameController to perform actions based on user input.
 *
 * @author Tai Zhi Xuan
 */
public class GameView {
    /**
     * The controller managing the game logic and interactions.
     */
    private final GameController controller;

    /**
     * Constructs a new GameView with the specified GameController.
     *
     * @param controller The GameController instance managing the game.
     */
    public GameView(GameController controller) {
        this.controller = controller;
    }

    /**
     * Starts the console-based game loop, handling user inputs and displaying game state.
     */
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

                Position from = new Position(fromRow, fromCol);
                Position to   = new Position(toRow, toCol);

                // Attempt the move
                if (controller.movePiece(from, to)) {
                    System.out.printf("%s moved from (%d, %d) to (%d, %d) successfully.%n",
                            currentPlayer, fromRow, fromCol, toRow, toCol);
                } else {
                    System.out.printf(
                            "Invalid move by %s. Try again.%n", currentPlayer
                    );
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Must be four integers (e.g., '0 1 0 2').");
                scanner.nextLine();
            }
        }

        System.out.println("Game over! The winner is " + controller.getCurrentPlayer());
        scanner.close();
    }

    /**
     * Displays the current state of the game board in the console.
     */
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

    /**
     * Saves the current game state to a specified filename.
     *
     * @param filename The name of the file to save the game state.
     */
    private void saveGame(String filename) {
        try {
            controller.saveGameAsText(filename);
            System.out.println("Game saved successfully to " + filename);
        } catch (Exception e) {
            System.out.println("Failed to save the game: " + e.getMessage());
        }
    }

    /**
     * Loads a game state from a specified filename.
     *
     * @param filename The name of the file from which to load the game state.
     */
    private void loadGame(String filename) {
        try {
            controller.loadGame(filename);
            System.out.println("Game loaded successfully from " + filename);
        } catch (Exception e) {
            System.out.println("Failed to load the game: " + e.getMessage());
        }
    }
}

