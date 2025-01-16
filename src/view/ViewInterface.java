// ViewInterface.java
package view;

import model.Position;

import java.util.List;

/**
 * Interface defining methods that views must implement.
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
     * Refreshes the entire board view.
     */
    void refreshBoard();

    /**
     * Displays a game over message and prompts for a new game or exit.
     *
     * @param message The game over message to display.
     */
    void gameOver(String message);
}
