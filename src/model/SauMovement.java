// SauMovement.java
package model;

/**
 * Sau moves 1 step in any direction, including diagonals.
 */
public class SauMovement implements MovementStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        if (from.equals(to)) return false;

        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());
        // 1 step in any direction
        if (rowDiff <= 1 && colDiff <= 1) {
            Piece mover = board.getPieceAt(from);
            Piece occupant = board.getPieceAt(to);
            return (occupant == null || occupant.getColor() != mover.getColor());
        }
        return false;
    }
}
