// GameState.java
package model;

import java.util.List;

public class GameState {
    private Board board;           // Contains the board state
    private Color currentPlayer;   // Current player
    private int turn;              // Turn counter
    private boolean isGameOver;    // Whether the game is over
    private List<Move> moveHistory; // List of moves made in the game
    private int secondsElapsed;    // New field for timer

    // Constructor and getter methods
    public GameState(Board board, Color currentPlayer, int turn, List<Move> moveHistory) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.turn = turn;
        this.moveHistory = moveHistory;
        this.isGameOver = false;  // Default to false, will be checked/updated elsewhere
        this.secondsElapsed = 0;  // Initialize timer
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

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    // Getter and setter for isGameOver
    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    // Getter and setter for secondsElapsed
    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }


    // Update game over status based on the game's board state
    public void updateGameOverStatus() {
        // Use the game's or board's logic to determine if the game is over
        // For example, you could check for remaining pieces or a winner
        this.isGameOver = board.isGameOver();  // Assuming you have isGameOver() in Board
    }
}
