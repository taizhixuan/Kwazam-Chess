// Game.java
package model;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private Board board;
    private Color currentPlayer;
    private boolean gameOver;

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Color.RED;
        this.gameOver = false;
    }

    public static void reset(Game game) {
        // Reset the board to its initial state
        game.board = new Board();  // This creates a new Board with all pieces in their starting positions

        // Reset the current player to the starting player
        game.currentPlayer = Color.RED; // Assuming the game starts with the White player

        // Reset the gameOver flag
        game.gameOver = false;
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
        Piece destinationPiece = board.getPieceAt(to);

        // If there is an opponent's Sau piece on the destination, remove it
        if (destinationPiece instanceof Sau && destinationPiece.getColor() != currentPlayer) {
            System.out.println("\n" + destinationPiece.getColor() + " Sau has been captured!");

            board.setPieceAt(to, piece); // Place the capturing piece in the captured Sau's position
            board.removePiece(from); // Remove the captured Sau piece
            piece.setPosition(to); // Update the capturing piece's position

            // Check if the game is over immediately after the Sau is removed
            checkGameOver();

            if (gameOver) {
                return true; // End the move and indicate the game is over
            }
        } else {
            // Execute the move
            board.setPieceAt(to, piece);
            board.removePiece(from);
            piece.setPosition(to);
        }

        // Check for transformations if required
        piece.transform(board);

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

    public void checkGameOver() {
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

        if (gameOver) {
            if (!redSauExists) {
                System.out.println("Blue wins!");
            } else {
                System.out.println("Red wins!");
            }
        }
    }

    /**
     * Determine the winner.
     */
    public String getWinner() {
        if (!gameOver) return null; // No winner will show if the game isn't over
        return (getCurrentPlayer() == Color.RED) ? "Red" : "Blue";
    }

    /* (Joyce)
    public Object getTurnCounter() {
        return null;
    }
     */
}
