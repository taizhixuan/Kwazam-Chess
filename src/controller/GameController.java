// GameController.java
package controller;

import model.*;
import view.BoardView;

import java.io.IOException;
import java.util.List;

/**
 * Connects the GUI (BoardView) with the Game model.
 */
public class GameController {
    private final Game game;
    private Position selectedPiece; // Stores the currently selected piece

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
        return game.movePiece(from, to);
    }

    /**
     * Handles tile clicks in the GUI.
     */
    public void handleTileClick(Position position, BoardView view) {
        // We'll figure out how to display row/col in logs shortly
        int clickedRow = position.getRow();
        int clickedCol = position.getColumn();

        // Check if current player is Red
        boolean isRed = getCurrentPlayer().equals("RED");

        // For logging, flip the row/col if Red is current player
        // (Matches your BoardView flipping: row=7 top -> 0 bottom, col=4 left -> 0 right)
        int displayClickedRow = isRed ? (7 - clickedRow) : clickedRow;
        int displayClickedCol = isRed ? (4 - clickedCol) : clickedCol;

        // If no piece is selected yet
        if (selectedPiece == null) {
            // Try to select a piece if it belongs to the current player
            Piece piece = game.getBoard().getPieceAt(position);
            if (piece != null && piece.getColor().name().equalsIgnoreCase(game.getCurrentPlayer().name())) {
                selectedPiece = position;
                List<Position> validMoves = game.getPossibleMoves(piece, position);

                // Flip for logs
                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: %s piece at (%d, %d) selected.%n",
                        piece.getClass().getSimpleName(),
                        displayFromRow, displayFromCol
                );

                view.clearHighlights();
                view.highlightSelectedPiece(position);
                view.highlightValidMoves(validMoves);
            }
        } else {
            // A piece is already selected
            // 1) If the user clicks the same piece => deselect
            if (selectedPiece.equals(position)) {
                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: Piece at (%d, %d) deselected.%n",
                        displayFromRow, displayFromCol
                );

                selectedPiece = null;
                view.clearHighlights();

            } else {
                // 2) If the user clicks another piece of the same color => ignore
                Piece pieceAtClicked = game.getBoard().getPieceAt(position);
                if (pieceAtClicked != null &&
                        pieceAtClicked.getColor().name().equalsIgnoreCase(game.getCurrentPlayer().name())) {
                    System.out.println("GUI: You must deselect the current piece first.");
                    return;
                }

                // 3) Otherwise, try to move the selected piece to the clicked tile

                // Flip for logs
                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: Attempting to move from (%d, %d) to (%d, %d).%n",
                        displayFromRow, displayFromCol,
                        displayClickedRow, displayClickedCol
                );

                if (game.movePiece(selectedPiece, position)) {
                    // Successful move

                    System.out.printf(
                            "GUI: Move successful: from (%d, %d) to (%d, %d).%n",
                            displayFromRow, displayFromCol,
                            displayClickedRow, displayClickedCol
                    );

                    view.clearHighlights();
                    view.refreshBoard();  // Refresh after a successful move

                    // If needed, refresh again if there's any extra logic after moves
                    if (game.getTurnCounter() % 2 == 0) {
                        view.refreshBoard();
                    }

                    // Check if the move ended the game
                    if (game.isGameOver()) {
                        String winnerMessage = game.getWinner() + " wins! Game Over.";
                        System.out.println("GUI: " + winnerMessage);
                        view.gameOver(winnerMessage);
                    }
                } else {
                    System.out.println("GUI: Invalid move. Try again.");
                }

                // Reset the selected piece
                selectedPiece = null;
                view.refreshBoard();  // Ensure final board state is shown
            }
        }
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
     *  Reset the game.
     */
    public void resetGame() {
        Game.reset(game); // Reset the game model with a new board
        selectedPiece = null; // Clear any selected piece
    }

    /**
     *  Save the game in .txt file.
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

    public void loadGame(String filename) {
        try {
            GameState gameState = GameSaver.loadGame(filename);

            // Restore the game state from the loaded file
            game.setBoard(gameState.getBoard());

            // Safely set the current player
            if (gameState.getCurrentPlayer() != null) {
                game.setCurrentPlayer(gameState.getCurrentPlayer().equalsIgnoreCase("blue") ? Color.BLUE : Color.RED);
            } else {
                System.err.println("Warning: Current player is null in the loaded game state.");
            }

            // Set the turn counter
            game.setTurn(gameState.getTurn());

            // Print success message
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }
    }

}
