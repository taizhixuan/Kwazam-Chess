// GameState.java
package model;

import java.io.Serial;
import java.io.Serializable;

public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Board board;      // The board object (includes piece positions)
    private String currentPlayer; // "blue" or "red"
    private int turn;  // Tracks the number of turns
    private boolean gameOver;  // Whether the game is over or not

    public GameState(Board board, Color currentPlayer, int turn) {
        this.board = board;
        this.currentPlayer = String.valueOf(currentPlayer);
        this.turn = turn;
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

    public int getTurn() {
        return turn;
    }

    public void setTurnCounter(int turn) {
        this.turn = turn;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
