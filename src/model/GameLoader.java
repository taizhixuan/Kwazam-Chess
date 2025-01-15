// GameLoader.java
package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;

public class GameLoader {

    public static GameState loadGameFromTextFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Board board = new Board();
            board.clearBoard();

            String line;
            int turn = 0;
            Color currentPlayer = null;
            List<Move> moveHistory = new ArrayList<>();
            int secondsElapsed = 0; // Initialize timer

            boolean loadingMoveHistory = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;

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
                } else if (line.startsWith("move:")) {
                    loadingMoveHistory = true;
                    String[] parts = line.substring(5).split(",\\s*");
                    if (parts.length != 6) { // Updated to expect 6 parts
                        throw new IOException("Invalid move format: " + line);
                    }
                    String player = parts[0].trim();
                    String pieceType = parts[1].trim(); // New: piece type
                    int fromRow = Integer.parseInt(parts[2].trim());
                    int fromCol = Integer.parseInt(parts[3].trim());
                    int toRow = Integer.parseInt(parts[4].trim());
                    int toCol = Integer.parseInt(parts[5].trim());

                    Position from = new Position(fromRow, fromCol);
                    Position to = new Position(toRow, toCol);

                    Move move = new Move(player, pieceType, from, to);
                    moveHistory.add(move);
                } else if (line.startsWith("secondsElapsed:")) {
                    secondsElapsed = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            // Create GameState with move history
            GameState gameState = new GameState(board, currentPlayer, turn, moveHistory);
            gameState.setSecondsElapsed(secondsElapsed); // Set timer state

            // Update game over status if necessary
            gameState.updateGameOverStatus();

            System.out.println("Game loaded successfully from " + filename);
            return gameState;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error loading game from text file: " + e.getMessage());
        }
    }
}