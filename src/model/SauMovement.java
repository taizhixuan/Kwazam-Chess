package model;

/**
 * The SauMovement class defines the movement logic specific to the Sau piece.
 *
 * Description:
 * Sau can move one step in any direction, including diagonals.
 * The move is valid if the target position is within bounds, not occupied by a friendly piece,
 * and follows the movement rules.
 *
 * Design Patterns:
 * - Strategy Pattern: Implements the MovementStrategy interface to encapsulate Sau's movement behavior.
 *
 * @author Joyce Ong Pay Teng
 */
public class SauMovement implements MovementStrategy {
    /**
     * Validates whether the Sau can move from the 'from' position to the 'to' position on the given board.
     *
     * @param from  The current position of the Sau.
     * @param to    The target position to move to.
     * @param board The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        if (from.equals(to)) return false;

        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());

        if (rowDiff <= 1 && colDiff <= 1) {
            Piece mover = board.getPieceAt(from);
            Piece occupant = board.getPieceAt(to);
            return (occupant == null || occupant.getColor() != mover.getColor());
        }
        return false;
    }
}
