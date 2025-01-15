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

    private final List<Move> moveHistory = new ArrayList<>();

    private int secondsElapsed = 0;

    public GameController(Board board) {
        this.game = new Game(board);
        this.selectedPiece = null;
    }

    // Getter for secondsElapsed
    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    // Setter for secondsElapsed
    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    // Method to increment secondsElapsed
    public void incrementSecondsElapsed() {
        this.secondsElapsed++;
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
        // Fetch the current player before making the move
        String currentPlayer = game.getCurrentPlayer().name();

        // Fetch the piece being moved to get its type
        Piece piece = game.getBoard().getPieceAt(from);
        if (piece == null) {
            return false; // No piece to move
        }
        String pieceType = piece.getType();

        boolean success = game.movePiece(from, to);
        if (success) {
            // Record the move with piece type
            Move move = new Move(currentPlayer, pieceType, from, to);
            moveHistory.add(move);
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

        // Check if current player is Red
        boolean isRed = getCurrentPlayer().equals("RED");

        int displayClickedRow = isRed ? (7 - clickedRow) : clickedRow;
        int displayClickedCol = isRed ? (4 - clickedCol) : clickedCol;

        // If no piece is currently selected
        if (selectedPiece == null) {
            Piece piece = game.getBoard().getPieceAt(position);

            // Make sure the tile has the current player's piece
            if (piece != null && piece.getColor().name().equalsIgnoreCase(game.getCurrentPlayer().name())) {
                // Select this piece
                selectedPiece = position;
                List<Position> validMoves = game.getPossibleMoves(piece, position);

                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: %s piece at (%d, %d) selected.%n",
                        piece.getClass().getSimpleName(),
                        displayFromRow, displayFromCol
                );

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
                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: Piece at (%d, %d) deselected.%n",
                        displayFromRow, displayFromCol
                );

                // The user clicked the same tile => deselect the piece
                selectedPiece = null;
                view.clearHighlights();

            } else {
                Piece pieceAtClicked = game.getBoard().getPieceAt(position);
                if (pieceAtClicked != null &&
                        pieceAtClicked.getColor().name().equalsIgnoreCase(game.getCurrentPlayer().name())) {
                    System.out.println("GUI: You must deselect the current piece first.");
                    return;
                }

                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: Attempting to move from (%d, %d) to (%d, %d).%n",
                        displayFromRow, displayFromCol,
                        displayClickedRow, displayClickedCol
                );

                if (movePiece(selectedPiece, position)) {
                    System.out.printf(
                            "GUI: Move successful: from (%d, %d) to (%d, %d).%n",
                            displayFromRow, displayFromCol,
                            displayClickedRow, displayClickedCol
                    );

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

    public List<Move> getMoveHistory() {
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
        moveHistory.clear(); // Clear move history when resetting
        secondsElapsed = 0;  // Reset the timer
    }

    /**
     * Save the game in .txt file.
     */
    public void saveGameAsText(String filename) {
        GameState gameState = new GameState(game.getBoard(), game.getCurrentPlayer(), game.getTurnCounter(), new ArrayList<>(moveHistory));        gameState.setSecondsElapsed(this.secondsElapsed); // Save timer state
        gameState.setSecondsElapsed(this.secondsElapsed); // Save timer state
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

            // Restore move history
            moveHistory.clear();
            moveHistory.addAll(gameState.getMoveHistory());

            // Restore timer state
            this.secondsElapsed = gameState.getSecondsElapsed();

            // Notify the GUI to update
            selectedPiece = null;
            System.out.println("Game loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }
    }
}
