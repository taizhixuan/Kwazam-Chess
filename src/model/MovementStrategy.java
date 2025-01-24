package model;

import java.io.Serializable;

/**
 * The MovementStrategy interface defines the contract for movement behaviors of chess pieces.
 * It allows different pieces to have unique movement rules by implementing this interface.
 *
 * Design Pattern: Strategy Pattern
 * Role: Strategy - Encapsulates a family of algorithms (movement rules),
 * making them interchangeable.
 *
 * @author Tai Zhi Xuan
 */
public interface MovementStrategy extends Serializable {
    /**
     * Determines whether moving from one position to another is valid for a specific piece.
     *
     * @param from  The current position of the piece.
     * @param to    The target position to move to.
     * @param board The current state of the game board.
     * @return True if the move is valid according to the piece's movement rules; false otherwise.
     */
    boolean isValidMove(Position from, Position to, Board board);
}
