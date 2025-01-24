package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Piece abstract class serves as the base for all chess pieces in Kwazam Chess.
 * It defines common properties and behaviors shared among different types of pieces.
 *
 * Design Patterns:
 * - Strategy Pattern: Utilizes MovementStrategy to delegate movement logic.
 * - Template Method Pattern: Provides a framework for piece behaviors like movement and transformation
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting
 */
public abstract class Piece {
    /**
     * The current position of the piece on the board.
     */
    protected Position position;

    /**
     * The color of the piece (RED or BLUE).
     */
    protected Color color;

    /**
     * The movement strategy defining how this piece can move.
     */
    protected MovementStrategy movementStrategy;

    /**
     * The number of moves this piece has made.
     */
    protected int moveCount;

    /**
     * The unique identifier for this piece.
     */
    private final int id;

    /**
     * Constructs a new Piece with the specified color and unique ID.
     *
     * @param color The color of the piece (RED or BLUE).
     * @param id    The unique identifier for this piece.
     */
    public Piece(Color color, int id) {
        this.color = color;
        this.moveCount = 0;
        this.id = id;
    }

    /**
     * Retrieves the color of the piece.
     *
     * @return The piece's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retrieves the current position of the piece.
     *
     * @return The piece's current Position.
     * @throws IllegalStateException If the position has not been initialized.
     */
    public Position getPosition() {
        if (position == null) {
            throw new IllegalStateException("Position not initialized.");
        }
        return position;
    }

    /**
     * Sets the position of the piece.
     *
     * @param position The new Position for the piece.
     * @throws IllegalArgumentException If the provided position is null.
     */
    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        this.position = position;
    }

    /**
     * Retrieves the number of moves this piece has made.
     *
     * @return The move count.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Increments the move count by one.
     */
    public void incrementMoveCount() {
        this.moveCount++;
    }

    /**
     * Determines if moving to the specified position is valid.
     * Delegates the validation to the piece's movement strategy.
     *
     * @param newPosition The target position to move to.
     * @param board       The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
    public boolean isValidMove(Position newPosition, Board board) {
        if (movementStrategy == null || position == null) {
            return false;
        }
        return movementStrategy.isValidMove(position, newPosition, board);
    }


    /**
     * Handles the transformation of the piece.
     * Specific to certain piece types like Tor and Xor.
     *
     * Design Pattern: Template Method Pattern
     * Role: Primitive Operation - Defines a method to be implemented by subclasses.
     *
     * @param board The current state of the game board.
     */
    public abstract void transform(Board board);

    /**
     * Handles actions after the piece has been moved.
     * This may include incrementing the move count and triggering transformations.
     *
     * @author Tai Zhi Xuan
     *
     * @param board The current state of the game board.
     */
    public void onMove(Board board) {
        incrementMoveCount();
        if (moveCount == 2) {
            transform(board);
        }
    }

    /**
     * Retrieves the file path to the piece's image for display in the GUI.
     *
     * @return The image path as a String.
     */
    public abstract String getImagePath();

    /**
     * Calculates all valid moves for this piece on the given board.
     *
     * @param board The current state of the game board.
     * @return A list of valid target positions for this piece.
     */
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position target = new Position(row, col);
                if (isValidMove(target, board)) {
                    validMoves.add(target);
                }
            }
        }
        return validMoves;
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The simple name of the piece's class.
     */
    public String getType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Retrieves the unique identifier of the piece.
     *
     * @return The piece's unique ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the move count for the piece.
     *
     * @param moveCount The new move count value.
     */
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}
