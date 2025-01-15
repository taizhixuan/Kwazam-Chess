// GameSaver.java
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

            writer.write("\n// The player to play a move\n");
            writer.write("currentPlayer: " + gameState.getCurrentPlayer() + "\n");

            writer.write("\n// Pieces on the board\n");
            writer.write("// Type, ID, row, col, Color\n");

            boolean isRedTurn = gameState.getCurrentPlayer().equals(Color.RED);

            for (int row = 0; row < gameState.getBoard().getRows(); row++) {
                for (int col = 0; col < gameState.getBoard().getColumns(); col++) {
                    Position position = new Position(row, col);
                    Piece piece = gameState.getBoard().getPieceAt(position);
                    if (piece != null) {
                        Position adjustedPosition = gameState.getBoard().rotateCoordinates(position, isRedTurn);

                        writer.write(String.format("piece: %s, %d, %d, %d, %s\n",
                                piece.getType(),
                                piece.getId(),
                                adjustedPosition.getRow(),
                                adjustedPosition.getColumn(),
                                piece.getColor()));
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
