// Game.java
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class represents the state and logic of the Kwazam Chess game.
 * It acts as the Subject in the Observer pattern, notifying observers of state changes.
 */
public class Game {
    private Board board;
    private Color currentPlayer;
    private boolean gameOver;
    private int turnCounter; // Tracks the total number of turns (Red + Blue moves count as one turn)
    private int turn; // Track the total number of turns (for each side)
    private final List<GameObserver> observers = new ArrayList<>();

    /**
     * Initializes a new game with the provided board.
     *
     * @param board The initial game board.
     */
    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Color.RED;
        this.gameOver = false;
        this.turnCounter = 0; // Initialize turn counter
        this.turn = 0; // Initialize turn
    }

    /**
     * Adds an observer to the game.
     *
     * @param observer The observer to add.
     */
    public synchronized void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the game.
     *
     * @param observer The observer to remove.
     */
    public synchronized void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a state change.
     *
     * @param event The type of event that occurred.
     */
    private void notifyObservers(GameEvent event) {
        List<GameObserver> observersCopy;
        synchronized(this) {
            observersCopy = new ArrayList<>(observers);
        }
        for (GameObserver observer : observersCopy) {
            observer.update(event);
        }
    }

    /**
     * Resets the game to its initial state.
     */
    public void reset() {
        this.board = new Board();  // Reinitialize the board with starting positions
        this.currentPlayer = Color.RED; // Reset to the starting player
        this.gameOver = false; // Reset game over flag
        this.turnCounter = 0; // Reset turn counter
        this.turn = 0; // Reset turn
        notifyObservers(GameEvent.RESET); // Notify observers of the reset
    }

    /**
     * Retrieves the list of possible moves for a given piece at a specified position.
     *
     * @param piece    The piece to move.
     * @param position The current position of the piece.
     * @return A list of valid target positions.
     */
    public List<Position> getPossibleMoves(Piece piece, Position position) {
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            return new ArrayList<>();
        }
        return piece.getValidMoves(board);
    }

    /**
     * Attempts to move a piece from one position to another.
     *
     * @param from The starting position.
     * @param to   The target position.
     * @return True if the move was successful, false otherwise.
     */
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

            // Notify observers about the move
            notifyObservers(GameEvent.MOVE);

            if (gameOver) {
                return true; // End the move and indicate the game is over
            }
        } else {
            // Execute the move
            board.setPieceAt(to, piece);
            board.removePiece(from);
            piece.setPosition(to);
            notifyObservers(GameEvent.MOVE); // Notify observers about the move
        }

        turn++;

        // After the move, check if the piece should transform
        piece.onMove(board);

        // Switch turn to the next player
        switchTurn();

        // Notify observers about the turn switch
        notifyObservers(GameEvent.TRANSFORM);

        return true;
    }

    /**
     * Retrieves the current game board.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
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
     * Switches the turn to the next player and handles transformations.
     */
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

    /**
     * Transforms Tor and Xor pieces as per game rules.
     */
    private void transformPieces() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPieceAt(position);

                if (piece instanceof Tor) {
                    int xorId = Board.IDGenerator.getXorId();
                    Xor newXor = new Xor(piece.getColor(), xorId);
                    newXor.setPosition(position);
                    board.setPieceAt(position, newXor);
                } else if (piece instanceof Xor) {
                    int torId = Board.IDGenerator.getTorId();
                    Tor newTor = new Tor(piece.getColor(), torId);
                    newTor.setPosition(position);
                    board.setPieceAt(position, newTor);
                }
            }
        }
        System.out.println("All Tor and Xor pieces have transformed!");

        // Notify observers about the transformation
        notifyObservers(GameEvent.TRANSFORM);
    }

    /**
     * Checks if the game is over by verifying the existence of Sau pieces.
     */
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

            // Notify observers about the game over
            notifyObservers(GameEvent.GAME_OVER);
        }
    }

    /**
     * Determines the winner of the game.
     *
     * @return The winner's color as a String, or null if the game isn't over.
     */
    public String getWinner() {
        if (!gameOver) return null;
        return (getCurrentPlayer() == Color.RED) ? "Red" : "Blue";
    }

    /**
     * Retrieves the current turn number.
     *
     * @return The current turn number.
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Retrieves the turn counter.
     *
     * @return The turn counter.
     */
    public int getTurnCounter() {
        return turnCounter;
    }

    /**
     * Sets a new board for the game and notifies observers.
     *
     * @param board The new game board.
     */
    public void setBoard(Board board) {
        this.board = board;
        notifyObservers(GameEvent.MOVE); // Assuming setting board is akin to a move
    }

    /**
     * Sets the current player and notifies observers.
     *
     * @param currentPlayer The new current player.
     */
    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
        notifyObservers(GameEvent.TRANSFORM); // Assuming a turn switch is related to transformation
    }

    /**
     * Sets the turn number and notifies observers.
     *
     * @param turn The new turn number.
     */
    public void setTurn(int turn) {
        this.turn = turn;
        notifyObservers(GameEvent.MOVE); // Assuming turn number relates to moves
    }

    /**
     * Sets the turn counter and notifies observers.
     *
     * @param turnCounter The new turn counter.
     */
    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
        notifyObservers(GameEvent.TRANSFORM); // Assuming turn counter relates to transformations
    }

    /**
     * Resets the game to its initial state and notifies observers.
     */
    public void resetGame() {
        this.board = new Board();
        this.currentPlayer = Color.RED;
        this.gameOver = false;
        this.turnCounter = 0;
        this.turn = 0;

        notifyObservers(GameEvent.RESET);
    }

    /**
     * Sets the game over status and notifies observers.
     *
     * @param gameOver The new game over status.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        notifyObservers(GameEvent.GAME_OVER);
    }
}
