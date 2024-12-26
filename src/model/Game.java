// Game.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private Color currentPlayer;
    private boolean gameOver;

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Color.RED;
        this.gameOver = false;
    }

    public List<Position> getPossibleMoves(Piece piece, Position position) {
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            return new ArrayList<>();
        }
        return piece.getValidMoves(board);
    }

    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPieceAt(from);

        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            System.out.println("Invalid move: Not your piece or no piece at position.");
            return false;
        }

        if (!piece.isValidMove(to, board)) {
            System.out.println("Invalid move: Cannot move to that position.");
            return false;
        }

        // Execute the move
        board.setPieceAt(to, piece);
        board.removePiece(from);
        piece.setPosition(to);

        // Check for transformations if required
        piece.transform(board);

        // Check if the game is over
        checkGameOver();

        // Switch turn to the next player
        switchTurn();

        return true;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == Color.RED) ? Color.BLUE : Color.RED;
    }

    private void checkGameOver() {
        boolean redSauExists = false;
        boolean blueSauExists = false;

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Piece piece = board.getPieceAt(new Position(row, col));
                if (piece instanceof Sau) {
                    if (piece.getColor() == Color.RED) {
                        redSauExists = true;
                    } else if (piece.getColor() == Color.BLUE) {
                        blueSauExists = true;
                    }
                }
            }
        }

        gameOver = !(redSauExists && blueSauExists);
    }
}
