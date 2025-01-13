package model;

import java.io.*;
import java.util.*;

public class GameLoader {

    public static GameState loadGameFromTextFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int turn = 0;
            Color currentPlayer = null;
            List<Piece> pieces = new ArrayList<>();

            // Read through the file line by line
            while ((line = reader.readLine()) != null) {
                // Skip empty lines and comments
                if (line.trim().isEmpty() || line.startsWith("//")) {
                    continue;
                }

                // Parse move count
                if (line.startsWith("moveCount:")) {
                    turn = Integer.parseInt(line.split(":")[1].trim());
                }

                // Parse current player
                if (line.startsWith("currentPlayer:")) {
                    String player = line.split(":")[1].trim();
                    currentPlayer = Color.valueOf(player); // Assumes Color is an enum with RED/BLUE
                }

                // Parse pieces on the board
                if (line.startsWith("piece:")) {
                    String[] parts = line.split(", ");
                    if (parts.length == 5) {
                        String type = parts[0].split(":")[1].trim();
                        int id = Integer.parseInt(parts[1].trim());
                        int row = Integer.parseInt(parts[2].trim());
                        int col = Integer.parseInt(parts[3].trim());
                        String color = parts[4].trim();

                        // Create the corresponding piece and add it to the pieces list
                        Piece piece = createPiece(type, id, color, new Position(row, col));
                        pieces.add(piece);
                    }
                }
            }

            // Create a new board and populate it with pieces
            Board board = new Board();
            for (Piece piece : pieces) {
                board.setPieceAt(piece.getPosition(), piece);  // Place each piece on the board
            }

            // Default value for isGameOver as false
            boolean isGameOver = false;  // Default value, can be changed based on logic if needed

            return new GameState(board, currentPlayer, turn);  // Only pass 3 parameters now

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error loading game from text file: " + e.getMessage());
        }
    }

    // Helper method to create a piece based on its type
    private static Piece createPiece(String type, int id, String color, Position position) {
        Color pieceColor = Color.valueOf(color); // Assumes Color is an enum with RED/BLUE
        Piece piece = null;

        // Create the corresponding piece and add it to the pieces list
        switch (type) {
            case "Tor":
                piece = new Tor(pieceColor, id); // Call the constructor with color and id
                break;
            case "Biz":
                piece = new Biz(pieceColor, id);
                break;
            case "Sau":
                piece = new Sau(pieceColor, id);
                break;
            case "Xor":
                piece = new Xor(pieceColor, id);
                break;
            case "Ram":
                piece = new Ram(pieceColor, id);
                break;
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }

        // Set the position after creation
        piece.setPosition(position);
        return piece;
    }
}
