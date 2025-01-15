// GameLoader.java
package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameLoader {

    public static GameState loadGameFromTextFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Board board = new Board();
            board.clearBoard();

            String line;
            int turn = 0;
            Color currentPlayer = null;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("//")) continue;

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

                    boolean isRedTurn = currentPlayer.equals(Color.RED);
                    Position adjustedPosition = new Position(row, col);
                    Position originalPosition = board.rotateCoordinates(adjustedPosition, isRedTurn);

                    Piece piece = PieceFactory.createPiece(type, color, id);
                    board.setPieceAt(originalPosition, piece);
                }
            }

            return new GameState(board, currentPlayer, turn);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error loading game from text file: " + e.getMessage());
        }
    }
}
