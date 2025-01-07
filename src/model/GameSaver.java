package model;

import controller.GameController;

import java.io.*;
import java.util.List;

public class GameSaver {

    // Save the game state to a binary file
    public static void saveGame(GameState gameState, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully to " + filename);
        }
    }

    // Save the game state to a text file in the specified format
    public static void saveGameAsText(GameState gameState, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("// The move count made\n");
            writer.write("moveCount: " + gameState.getTurn() + "\n");
            writer.write("\n" + "// The player to play a move\n");
            writer.write("currentPlayer: " + gameState.getCurrentPlayer() + "\n");

            // Log game-over status
            writer.write("\n" + "// Whether the game is over\n");
            writer.write("isGameOver: " + gameState.isGameOver() + "\n");

            // Write each piece on the board
            writer.write("// Pieces on the board\n");
            writer.write("\n" + "// Type, ID, y Position, x Position, Color, direction (for Point)\n");
            for (int row = 0; row < gameState.getBoard().getRows(); row++) {
                for (int col = 0; col < gameState.getBoard().getColumns(); col++) {
                    Piece piece = gameState.getBoard().getPieceAt(new Position(row, col));
                    if (piece != null) {
                        // Log each piece's details
                        String line = "piece: " +
                                piece.getType() + ", " +
                                piece.getId() + ", " +
                                row + ", " + col + ", " +
                                piece.getColor();
                        writer.write(line + "\n");
                    }
                }
            }
            System.out.println("Game saved successfully to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving game as text: " + e.getMessage());
        }
    }


    // Load the game state from a binary file
    public static GameState loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) ois.readObject();
        }
    }
}
