package view;

import model.Position;

import java.util.List;

/**
 * ViewInterface defines the contract that all view components must adhere to.
 * It ensures consistency across different views in the application.
 *
 * @author Tai Zhi Xuan
 */
public interface ViewInterface {
    /**
     * Highlights the valid move positions on the board.
     *
     * @param validMoves List of valid positions to highlight.
     */
    void highlightValidMoves(List<Position> validMoves);

    /**
     * Clears all highlighted positions on the board.
     */
    void clearHighlights();

    /**
     * Refreshes the entire board view, updating piece positions and UI elements.
     */
    void refreshBoard();

    /**
     * Displays a game over message and prompts the user to start a new game or exit.
     *
     * @param message The game over message to display.
     */
    void gameOver(String message);
}
