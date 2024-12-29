// GameController.java
package controller;

import model.Board;
import model.Game;
import model.Piece;
import model.Position;
import view.BoardView;

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
        if (selectedPiece == null) {
            // First click: Select a piece
            Piece piece = game.getBoard().getPieceAt(position);
            if (piece != null && piece.getColor() == game.getCurrentPlayer()) {
                selectedPiece = position; // Remember the selected piece
                List<Position> validMoves = game.getPossibleMoves(piece, position);
                view.clearHighlights();
                view.highlightValidMoves(validMoves); // Highlight valid moves
            }
        } else {
            // Second click: Attempt to move the piece
            if (game.movePiece(selectedPiece, position)) {
                view.clearHighlights(); // Clear highlights after a successful move
                view.refreshBoard(); // Refresh the GUI immediately

                if (game.isGameOver()) {
                    Piece capturingPiece = game.getBoard().getPieceAt(position);

                    // Notify the GUI about the game-over scenario
                    String winnerMessage = game.getWinner() + " wins! Game Over.";
                    view.gameOver(winnerMessage);

                    selectedPiece = null; // Reset selection
                    return;
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
            selectedPiece = null; // Reset selection
            view.refreshBoard(); // Refresh the GUI
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
}