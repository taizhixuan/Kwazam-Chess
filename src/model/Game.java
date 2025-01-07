// Game.java
package model;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private Board board;
    private Color currentPlayer;
    private boolean gameOver;
    private int turnCounter; // Tracks the total number of turns (Red + Blue moves count as one turn)
    private int turn; // Track the total number of turns (for each side)

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Color.RED;
        this.gameOver = false;
        this.turnCounter = 0; // Initialize turn counter
        this.turn = 0; // Initialize turn
    }

    public static void reset(Game game) {
        // Reset the board to its initial state
        game.board = new Board();  // This creates a new Board with all pieces in their starting positions

        // Reset the current player to the starting player
        game.currentPlayer = Color.RED; // Assuming the game starts with the White player

        // Reset the gameOver flag
        game.gameOver = false;
        game.turn = 0; // Reset the turn counter
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

        turn++;

        // After the move, check if the piece should transform
        piece.onMove(board);

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

        // Increment turn counter only after both players have moved (one full turn)
        if (currentPlayer == Color.RED) {
            turnCounter++;
            if (turnCounter % 2 == 0) { // After every 2 full turns (one Blue + one Red move)
                transformPieces();
            }
        }
    }

    private void transformPieces() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPieceAt(position);

                if (piece instanceof Tor) {
                    Xor newXor = new Xor(piece.getColor());
                    newXor.setPosition(position);
                    board.setPieceAt(position, newXor);
                } else if (piece instanceof Xor) {
                    Tor newTor = new Tor(piece.getColor());
                    newTor.setPosition(position);
                    board.setPieceAt(position, newTor);
                }
            }
        }
        System.out.println("All Tor and Xor pieces have transformed!");
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

    public int getTurn() {
        return turn;
    }

    public int getTurnCounter() {
        return turn;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public void resetGame() {
        this.board = new Board(); // Reinitialize the board
        this.currentPlayer = Color.RED; // Reset to the default starting player
        this.gameOver = false; // Reset the game over flag
        this.turnCounter = 0; // Reset the turn counter
    }


}
