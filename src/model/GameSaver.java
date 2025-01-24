package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The GameSaver class handles saving the current state of the game to a text file.
 * It serializes the board state, current player, turn counts, and move history.
 *
 * Design Pattern: Singleton Pattern (for PieceFactory)
 * Role: Utilizes PieceFactory to recreate pieces.
 *
 * @author Tiffany Jong Shu Ting
 */
public class GameSaver {

    /**
     * Saves the provided GameState to a text file in a structured format.
     *
     * @param gameState The current state of the game to be saved.
     * @param filename  The name of the file to which the game state will be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
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
                        move.getPlayer(),       // Player who made the move
                        move.getPieceType(),    // Type of piece moved
                        move.getFrom().getRow(),    // Starting row
                        move.getFrom().getColumn(), // Starting column
                        move.getTo().getRow(),      // Target row
                        move.getTo().getColumn()    // Target column
                ));
            }

            // Write the timer state
            writer.write("\n// --- Timer State ---\n");
            writer.write("secondsElapsed: " + gameState.getSecondsElapsed() + "\n");

            // End message
            writer.write("\n// --- End of Game State ---\n");
            System.out.println("Game saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to save the game to " + filename + ": " + e.getMessage());
        }
    }
}
