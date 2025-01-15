// GameSaver.java
package model;

import java.io.*;
import java.util.List;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;

public class GameSaver {

    // Save the game state to a text file in the specified format
    public static void saveGameAsText(GameState gameState, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write the turn count
            writer.write("// --- Game State ---\n");
            writer.write("// Move count (total number of moves made in the game)\n");
            writer.write("moveCount: " + gameState.getTurn() + "\n\n");

            // Write the current player
            writer.write("// Current player to move (RED or BLUE)\n");
            writer.write("currentPlayer: " + gameState.getCurrentPlayer() + "\n\n");

            // Write the board state
            writer.write("// --- Board State ---\n");
            writer.write("// Format: piece: [Type], [ID], [Row], [Col], [Color]\n");

            boolean isRedTurn = gameState.getCurrentPlayer().equals(Color.RED);
            for (int row = 0; row < gameState.getBoard().getRows(); row++) {
                for (int col = 0; col < gameState.getBoard().getColumns(); col++) {
                    Position position = new Position(row, col);
                    Piece piece = gameState.getBoard().getPieceAt(position);
                    if (piece != null) {
                        // Adjust coordinates based on the current player's perspective
                        Position adjustedPosition = gameState.getBoard().rotateCoordinates(position, isRedTurn);

                        writer.write(String.format("piece: %s, %d, %d, %d, %s\n",
                                piece.getType(),      // Type of piece
                                piece.getId(),        // Unique ID
                                adjustedPosition.getRow(),   // Row (adjusted for player's view)
                                adjustedPosition.getColumn(), // Column (adjusted for player's view)
                                piece.getColor()      // Piece color (RED/BLUE)
                        ));
                    }
                }
            }

            // Write the move history
            writer.write("\n// --- Move History ---\n");
            writer.write("// Format: move: [Player], [PieceType], [FromRow], [FromCol], [ToRow], [ToCol]\n");

            List<Move> moveHistory = gameState.getMoveHistory();
            for (Move move : moveHistory) {
                writer.write(String.format("move: %s, %s, %d, %d, %d, %d\n",
                        move.getPlayer(),
                        move.getPieceType(),
                        move.getFrom().getRow(),
                        move.getFrom().getColumn(),
                        move.getTo().getRow(),
                        move.getTo().getColumn()
                ));
            }

            // End message
            writer.write("\n// --- End of Game State ---\n");
            System.out.println("Game saved successfully to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving game as text: " + e.getMessage());
        }
    }
}
