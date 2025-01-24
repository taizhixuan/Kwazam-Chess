package model;

/**
 * The RamMovement class defines the movement logic specific to the Ram piece.
 *
 * Description:
 * Ram can move one step forward based on its current direction
 * The move is valid if the target position is within bounds, not occupied by a friendly piece,
 * and adheres to the movement direction.
 *
 * Design Patterns:
 * - Strategy Pattern: Implements the MovementStrategy interface to encapsulate Ram's movement behavior.
 *
 * @author Tai Zhi Xuan
 */
public class RamMovement implements MovementStrategy {
    /**
     * Validates whether the Ram can move from the 'from' position to the 'to' position on the given board.
     *
     * @param from  The current position of the Ram.
     * @param to    The target position to move to.
     * @param board The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        Piece piece = board.getPieceAt(from);
        if (!(piece instanceof Ram)) return false;

        Ram ram = (Ram) piece;
        if (from.equals(to)) return false;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getColumn() - from.getColumn();

        // Determine the expected row step based on direction
        int expectedRowStep = 0;
        if (ram.isGoingForward()) {
            expectedRowStep = (ram.getColor() == Color.RED) ? +1 : -1;
        } else {
            expectedRowStep = (ram.getColor() == Color.RED) ? -1 : +1;
        }

        // Ram must move exactly one step in the forward or backward direction with no horizontal movement
        if (rowDiff == expectedRowStep && colDiff == 0) {
            Piece occupant = board.getPieceAt(to);
            // Move is valid if the target square is empty or occupied by an opponent's piece
            return occupant == null || occupant.getColor() != ram.getColor();
        }
        return false; // Invalid move
    }
}
