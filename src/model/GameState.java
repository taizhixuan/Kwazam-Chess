package model;

import java.util.List;

/**
 * The GameState class encapsulates all relevant information about the current state of the game.
 * It includes the board setup, current player, turn counts, move history, and timer state.
 *
 * Design Patterns:
 * - Memento Pattern: Acts as a Memento by capturing and externalizing the game's state.
 * - State Pattern: Represents a concrete state within the game's lifecycle.<
 *
 * State Pattern Roles:
 *   State: Interface or abstract class defining state-specific behaviors.
 *   ConcreteState: GameState class implementing state-specific behaviors.
 *   Context: Game class that maintains an instance of a State subclass.
 *
 * @author Tiffany Jong Shu Ting
 */
public class GameState {
    /**
     * The current state of the game board.
     */
    private Board board;

    /**
     * The color of the player whose turn it is.
     */
    private Color currentPlayer;

    /**
     * The total number of individual player turns taken.
     */
    private int turn;

    /**
     * Flag indicating whether the game has ended.
     */
    private boolean isGameOver;

    /**
     * A list of all moves made during the game.
     */
    private List<Move> moveHistory;

    /**
     * The number of seconds elapsed since the game started.
     */
    private int secondsElapsed;

    /**
     * Constructs a new GameState with the provided parameters.
     *
     * @param board         The current game board.
     * @param currentPlayer The current player.
     * @param turn          The current turn number.
     * @param moveHistory   The history of moves made.
     */
    public GameState(Board board, Color currentPlayer, int turn, List<Move> moveHistory) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.turn = turn;
        this.moveHistory = moveHistory;
        this.isGameOver = false;
        this.secondsElapsed = 0;
    }

    /**
     * Retrieves the game board.
     *
     * @return The current game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player's color.
     */
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Retrieves the current turn number.
     *
     * @return The turn number.
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Retrieves the history of moves made in the game.
     *
     * @return A list of Move objects representing the move history.
     */
    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    /**
     * Checks whether the game has ended.
     *
     * @return True if the game is over; false otherwise.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Sets the game over status.
     *
     * @param isGameOver True to mark the game as over; false otherwise.
     */
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    /**
     * Retrieves the number of seconds elapsed since the game started.
     *
     * @return The elapsed time in seconds.
     */
    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    /**
     * Sets the number of seconds elapsed since the game started.
     *
     * @param secondsElapsed The new elapsed time in seconds.
     */
    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    /**
     * Updates the game over status based on the current board state.
     * Sets isGameOver to true if either player has no Sau remaining.
     */
    public void updateGameOverStatus() {

        this.isGameOver = board.isGameOver();
    }
}
