// GameState.java
package model;

import java.io.Serial;
import java.io.Serializable;

public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Board board;      // The board object (includes piece positions)
    private String currentPlayer; // "blue" or "red"
    private int turnCounter;  // Tracks the number of turns

    public GameState(Board board, Color currentPlayer, Object turnCounter) {
        this.board = board;
        this.currentPlayer = String.valueOf(currentPlayer);
        this.turnCounter = (int) turnCounter;
    }

    // Getters and Setters
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }
}
