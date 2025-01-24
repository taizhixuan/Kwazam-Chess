package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class encapsulates the state and logic of a Kwazam Chess game.
 * It manages the game board, player turns, move history, and determines game outcomes.
 *
 * Design Pattern: Observer Pattern
 *Role: Subject - Maintains a list of observers and notifies them of state changes.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
 */
public class Game {
    /**
     * The game board containing all pieces and their positions.
     */
    private Board board;

    /**
     * The color of the current player (RED or BLUE).
     */
    private Color currentPlayer;

    /**
     * Flag indicating whether the game has ended.
     */
    private boolean gameOver;

    /**
     * Tracks the total number of turns taken by both players.
     * (Red and Blue moves together count as one full turn.)
     */
    private int turnCounter;

    /**
     * Tracks the total number of individual player turns.
     */
    private int turn;

    /**
     * List of observers subscribed to game events.
     */
    private final List<GameObserver> observers = new ArrayList<>();

    /**
     * Constructs a new Game instance with the provided board.
     * Initializes the starting player and turn counters.
     *
     * @param board The initial game board setup.
     */
    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Color.RED;
        this.gameOver = false;
        this.turnCounter = 0;
        this.turn = 0;
    }

    /**
     * Adds an observer to the game.
     * Observers will be notified of game events such as moves, resets, and game over.
     *
     * @param observer The observer to be added.
     */
    public synchronized void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the game.
     *
     * @param observer The observer to be removed.
     */
    public synchronized void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a specific game event.
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
     * Clears the board, resets player turns, and notifies observers.
     */
    public void reset() {
        this.board = new Board();
        this.currentPlayer = Color.RED;
        this.gameOver = false;
        this.turnCounter = 0;
        this.turn = 0;
        notifyObservers(GameEvent.RESET);
    }

    /**
     * Retrieves a list of possible moves for a given piece at a specified position.
     *
     * @param piece    The piece for which to calculate moves.
     * @param position The current position of the piece.
     * @return A list of valid target positions for the piece.
     */
    public List<Position> getPossibleMoves(Piece piece, Position position) {
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            return new ArrayList<>();
        }
        return piece.getValidMoves(board);
    }

    /**
     * Attempts to move a piece from one position to another.
     * Validates the move, executes it if valid, handles captures, and updates game state.
     *
     * @param from The starting position of the piece.
     * @param to   The target position to move the piece to.
     * @return True if the move was successful; false otherwise.
     */
    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPieceAt(from);

        // Validate that the piece exists and belongs to the current player
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            System.out.println("Invalid move: Not your piece or no piece at position.");
            return false;
        }

        // Validate the move using the piece's movement strategy
        if (!piece.isValidMove(to, board)) {
            System.out.println("Invalid move: Cannot move to that position.");
            return false;
        }

        // Execute the move
        Piece destinationPiece = board.getPieceAt(to);

        // Handle capturing an opponent's Sau
        if (destinationPiece instanceof Sau && destinationPiece.getColor() != currentPlayer) {
            System.out.println("\n" + destinationPiece.getColor() + " Sau has been captured!");

            board.setPieceAt(to, piece); // Move the piece to the destination
            board.removePiece(from); // Remove the captured Sau
            piece.setPosition(to); // Update the piece's position

            // Check if the game has ended after the capture
            checkGameOver();

            // Notify observers about the move
            notifyObservers(GameEvent.MOVE);

            if (gameOver) {
                return true; // Exit if the game is over
            }
        } else {
            // Standard move without capture
            board.setPieceAt(to, piece); // Move the piece to the destination
            board.removePiece(from); // Remove the piece from the original position
            piece.setPosition(to); // Update the piece's position
            notifyObservers(GameEvent.MOVE); // Notify observers about the move
        }

        turn++; // Increment individual turn counter

        // Handle transformation if applicable
        piece.onMove(board);

        // Switch to the next player
        switchTurn();

        // Notify observers about the turn switch and potential transformations
        notifyObservers(GameEvent.TRANSFORM);

        return true; // Move was successful
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
     * Checks whether the game has ended.
     *
     * @return True if the game is over; false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Retrieves the color of the current player.
     *
     * @return The current player's color.
     */
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Switches the turn to the next player.
     * Handles transformation of pieces after a complete turn cycle.
     */
    private void switchTurn() {
        currentPlayer = (currentPlayer == Color.RED) ? Color.BLUE : Color.RED;

        // Increment turn counter after both players have moved (one full turn)
        if (currentPlayer == Color.RED) {
            turnCounter++;
            if (turnCounter % 2 == 0) { // After every 2 full turns (Red + Blue)
                transformPieces();
            }
        }
    }

    /**
     * Transforms Tor and Xor pieces as per game rules.
     * Replaces Tor with Xor and vice versa.
     */
    private void transformPieces() {
        IDGenerator idGen = IDGenerator.getInstance(); // Obtain Singleton instance

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPieceAt(position);

                if (piece instanceof Tor) {
                    Piece newXor = PieceFactory.createPiece("xor", piece.getColor(), idGen.getXorId());
                    newXor.setPosition(position);
                    board.setPieceAt(position, newXor);
                } else if (piece instanceof Xor) {
                    Piece newTor = PieceFactory.createPiece("tor", piece.getColor(), idGen.getTorId());
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
     * Checks if the game has ended by verifying the existence of Sau pieces.
     * If either player has no Sau remaining, the game is over.
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
     * Determines the winner of the game based on the remaining Sau pieces.
     *
     * @return The winner's color as a String ("Red" or "Blue"), or null if the game isn't over.
     */
    public String getWinner() {
        if (!gameOver) return null;
        return (getCurrentPlayer() == Color.RED) ? "Red" : "Blue";
    }

    /**
     * Retrieves the current individual turn number.
     *
     * @return The current turn number.
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Retrieves the current full turn counter.
     *
     * @return The turn counter.
     */
    public int getTurnCounter() {
        return turnCounter;
    }

    /**
     * Sets a new board for the game and notifies observers of the change.
     *
     * @param board The new game board.
     */
    public void setBoard(Board board) {
        this.board = board;
        notifyObservers(GameEvent.MOVE);
    }

    /**
     * Sets the current player and notifies observers of the turn switch.
     *
     * @param currentPlayer The new current player.
     */
    public void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
        notifyObservers(GameEvent.TRANSFORM);
    }

    /**
     * Sets the current individual turn number and notifies observers.
     *
     * @param turn The new turn number.
     */
    public void setTurn(int turn) {
        this.turn = turn;
        notifyObservers(GameEvent.MOVE);
    }

    /**
     * Sets the full turn counter and notifies observers.
     *
     * @param turnCounter The new turn counter value.
     */
    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
        notifyObservers(GameEvent.TRANSFORM);
    }

    /**
     * Resets the game to its initial state, including the board, player turns, and turn counters.
     * Notifies observers of the reset.
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
