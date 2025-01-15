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
                        // Adjust coordinates based on the current player's perspective
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

    public static GameState loadGame(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Board board = new Board();
            Color currentPlayer = null;
            int turn = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("moveCount:")) {
                    turn = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("currentPlayer:")) {
                    currentPlayer = Color.valueOf(line.split(":")[1].trim());
                } else if (line.startsWith("piece:")) {
                    String[] parts = line.split(", ");
                    String type = parts[0].split(":")[1].trim();
                    int id = Integer.parseInt(parts[1].trim());
                    int row = Integer.parseInt(parts[2].trim());
                    int col = Integer.parseInt(parts[3].trim());
                    Color color = Color.valueOf(parts[4].trim());

                    Position position = new Position(row, col);
                    Piece piece = PieceFactory.createPiece(type, color, id);
                    board.setPieceAt(position, piece);
                }
            }

            return new GameState(board, currentPlayer, turn);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error loading game from text file: " + e.getMessage());
        }
    }
}
