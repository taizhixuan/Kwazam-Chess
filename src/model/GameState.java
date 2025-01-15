// GameState.java
package model;

public class GameState {
    private Board board;           // Contains the board state
    private Color currentPlayer;   // Current player
    private int turn;              // Turn counter
    private boolean isGameOver;    // Whether the game is over

    // Constructor and getter methods
    public GameState(Board board, Color currentPlayer, int turn) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.turn = turn;
        this.isGameOver = false;  // Default to false, will be checked/updated elsewhere
    }

    public Board getBoard() {
        return board;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurn() {
        return turn;
    }

    // Getter for isGameOver
    public boolean isGameOver() {
        return isGameOver;
    }

    // Setter for isGameOver (in case you want to manually set it)
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    // Update game over status based on the game's board state
    public void updateGameOverStatus() {
        // Use the game's or board's logic to determine if the game is over
        // For example, you could check for remaining pieces or a winner
        this.isGameOver = board.isGameOver();  // Assuming you have isGameOver() in Board
    }
}
