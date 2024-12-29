package model;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;  // For version control in serialization

    String[][] board;  // 5x8 board, stores piece information
    String turn;       // "blue" or "red"
    int turnCounter;   // Tracks the number of turns (to handle Tor/Xor transformations)
    PieceState[] pieces;  // Stores state of all pieces (position, type, etc.)

    // Constructor for initializing the game state
    public GameState(String[][] board, String turn, int turnCounter, PieceState[] pieces) {
        this.board = board;
        this.turn = turn;
        this.turnCounter = turnCounter;
        this.pieces = pieces;
    }

    // Getters and setters for each field (if needed)
    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public PieceState[] getPieces() {
        return pieces;
    }

    public void setPieces(PieceState[] pieces) {
        this.pieces = pieces;
    }
}


