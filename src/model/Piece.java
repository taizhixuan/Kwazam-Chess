// Piece.java
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all Kwazam Chess pieces.
 */
public abstract class Piece {
    protected Position position; // Current position of the piece
    protected Color color; // Color of the piece (RED or BLUE)
    protected MovementStrategy movementStrategy; // Movement logic for this piece
    protected int moveCount; // Tracks the number of moves this piece has made

    public Piece(Color color) {
        this.color = color;
        this.moveCount = 0; // Initialize move count
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        if (position == null) {
            throw new IllegalStateException("Position not initialized.");
        }
        return position;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        this.position = position;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void incrementMoveCount() {
        this.moveCount++;
    }

    /**
     * Checks if a move to the specified position is valid.
     */
    public boolean isValidMove(Position newPosition, Board board) {
        if (movementStrategy == null || position == null) {
            return false;
        }
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    /**
     * Handles transformations for pieces like Tor -> Xor or Xor -> Tor.
     */
    public abstract void transform(Board board);

    /**
     * Handles actions after a move, such as incrementing move count or flipping directions.
     */
    public void onMove(Board board) {
        incrementMoveCount();
        if (moveCount == 2) { // Check if transformation condition is met
            transform(board); // Trigger the transformation
        }
    }

    /**
     * Returns the file path of the piece's image for display in the GUI.
     */
    public abstract String getImagePath();

    /**
     * Calculates all valid moves for this piece on the given board.
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
}
