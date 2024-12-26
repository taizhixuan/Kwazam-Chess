// MovementStrategy.java
package model;

import java.io.Serializable;

/**
 * Strategy interface for piece movement rules.
 */
public interface MovementStrategy extends Serializable {
    /**
     * Checks if moving from "from" to "to" is valid for a given piece type.
     */
    boolean isValidMove(Position from, Position to, Board board);
}
