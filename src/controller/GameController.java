// GameController.java
package controller;

import model.*;
import view.BoardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Connects the GUI (BoardView) with the Game model.
 */
public class GameController {
    private final Game game;
    private Position selectedPiece; // Stores the currently selected piece

    private final List<String> moveHistory = new ArrayList<>();


    public GameController(Board board) {
        this.game = new Game(board);
        this.selectedPiece = null;
    }

    public boolean isPieceSelected() {
        return selectedPiece != null;
    }

    public Position getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Handles a move request from a position to another.
     */
    public boolean movePiece(Position from, Position to) {
        boolean success = game.movePiece(from, to);
        if (success) {
            // Record the move in the moveHistory list
            String currentPlayer = game.getCurrentPlayer().name();
            // Example format: "RED: (1,2) -> (2,2)"
            String moveDesc = String.format(
                    "%s: (%d,%d) -> (%d,%d)",
                    currentPlayer, from.getRow(), from.getColumn(),
                    to.getRow(), to.getColumn()
            );
            moveHistory.add(moveDesc);
        }
        return success;
    }

    /**
     * Handles tile clicks in the GUI.
     */
    public void handleTileClick(Position position, BoardView view) {
        // Simply use the row/col from the clicked position, with no flipping
        int clickedRow = position.getRow();
        int clickedCol = position.getColumn();

        // If no piece is currently selected
        if (selectedPiece == null) {
            Piece piece = game.getBoard().getPieceAt(position);

            // Make sure the tile has the current player's piece
            if (piece != null && piece.getColor().name().equalsIgnoreCase(game.getCurrentPlayer().name())) {
                // Select this piece
                selectedPiece = position;
                List<Position> validMoves = game.getPossibleMoves(piece, position);

                // Log a simple console message
                System.out.printf("GUI: %s piece at (%d, %d) selected.%n",
                        piece.getClass().getSimpleName(), clickedRow, clickedCol);

                // Highlight the selected piece + its valid moves
                view.clearHighlights();
                view.highlightSelectedPiece(position);
                view.highlightValidMoves(validMoves);

            } else {
                // Either the tile is empty or it's the opponent's piece
                System.out.println("GUI: No valid piece selected or it's the opponent's piece.");
            }
        } else {
            // A piece was already selected
            if (selectedPiece.equals(position)) {
                // The user clicked the same tile => deselect the piece
                Piece piece = game.getBoard().getPieceAt(position);
                System.out.printf("GUI: %s piece at (%d, %d) deselected.%n",
                        (piece != null) ? piece.getClass().getSimpleName() : "Unknown",
                        clickedRow, clickedCol);

                selectedPiece = null;
                view.clearHighlights();

            } else {
                // Attempt to move from selectedPiece to this clicked tile
                System.out.printf("GUI: Attempting to move from (%d, %d) to (%d, %d).%n",
                        selectedPiece.getRow(), selectedPiece.getColumn(),
                        clickedRow, clickedCol);

                if (game.movePiece(selectedPiece, position)) {
                    // If move is successful, log it and refresh
                    System.out.printf("GUI: Move successful: from (%d, %d) to (%d, %d).%n",
                            selectedPiece.getRow(), selectedPiece.getColumn(),
                            clickedRow, clickedCol);

                    view.clearHighlights();
                    view.refreshBoard();

                    // Check if the game is over
                    if (game.isGameOver()) {
                        String winnerMessage = game.getWinner() + " wins! Game Over.";
                        System.out.println("GUI: " + winnerMessage);
                        view.gameOver(winnerMessage);
                    }
                } else {
                    // If move is invalid
                    System.out.println("GUI: Invalid move. Try again.");
                }

                // Clear the selection either way
                selectedPiece = null;
                // Re-draw the board to remove or update highlights
                view.refreshBoard();
            }
        }
    }

    public List<String> getMoveHistory() {
        return Collections.unmodifiableList(moveHistory);
    }

    /**
     * Gets the current game board.
     */
    public Board getBoard() {
        return game.getBoard();
    }

    /**
     * Checks if the game is over.
     */
    public boolean isGameOver() {
        return game.isGameOver();
    }

    /**
     * Determine the winner
     */
    public String getWinner() {
        return game.getWinner();
    }

    /**
     * Gets the current player.
     */
    public String getCurrentPlayer() {
        return game.getCurrentPlayer().name();
    }

    /**
     * Reset the game.
     */
    public void resetGame() {
        Game.reset(game);
        selectedPiece = null;
    }

    /**
     * Save the game in .txt file.
     */
    public void saveGameAsText(String filename) {
        GameState gameState = new GameState(game.getBoard(), game.getCurrentPlayer(), game.getTurnCounter());
        try {
            GameSaver.saveGameAsText(gameState, filename);
            System.out.println("Game saved as text successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save the game as text.");
        }
    }

    /**
     * Load the game from a text file.
     */
    public void loadGame(String filename) {
        try {
            GameState gameState = GameLoader.loadGameFromTextFile(filename);

            // Restore the game state from the loaded file
            game.setBoard(gameState.getBoard());
            game.setCurrentPlayer(gameState.getCurrentPlayer());
            game.setTurnCounter(gameState.getTurn());

            // Notify the GUI to update
            selectedPiece = null;
            System.out.println("Game loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }
    }
}
