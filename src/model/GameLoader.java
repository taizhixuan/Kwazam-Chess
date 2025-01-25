package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameLoader class handles loading game states from text files.
 * It parses the file and reconstructs the GameState, including the board setup,
 * current player, turn counts, and move history.
 *
 * Design Pattern: Factory Pattern
 * Role: Utilizes PieceFactory to create piece instances.
 *
 * @author Joyce Ong Pay Teng
 */
public class GameLoader {

    /**
     * Loads a game state from a specified text file.
     *
     * @param filename The path to the game state text file.
     * @return A GameState object representing the loaded game.
     * @throws IOException If an error occurs while reading the file.
     */
    public static GameState loadGameFromTextFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Board board = new Board();
            board.clearBoard();

            String line;
            int turn = 0;
            Color currentPlayer = null;
            List<Move> moveHistory = new ArrayList<>();
            int secondsElapsed = 0;

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
                    Position adjustedPosition = board.rotateCoordinates(new Position(row, col), isRedTurn);

                    Piece piece = PieceFactory.createPiece(type, color, id);
                    piece.setPosition(adjustedPosition);
                    board.setPieceAt(adjustedPosition, piece);
                } else if (line.startsWith("move:")) {
                    String[] parts = line.substring(5).split(",\\s*");
                    if (parts.length != 6) {
                        throw new IOException("Invalid move format: " + line);
                    }
                    String player = parts[0].trim();
                    String pieceType = parts[1].trim();
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

            GameState gameState = new GameState(board, currentPlayer, turn, moveHistory);
            gameState.setSecondsElapsed(secondsElapsed);

            gameState.updateGameOverStatus();

            System.out.println("Game loaded successfully from " + filename);
            return gameState;
        } catch (IOException e) {
            throw new IOException("Error reading the game file: " + filename, e);
        }
    }
}