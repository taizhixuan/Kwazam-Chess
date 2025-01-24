package model;

/**
 * The BizMovement class encapsulates the movement logic specific to the Biz piece.
 * It implements the MovementStrategy interface, allowing Biz to define its own
 * rules for valid movements.
 *
 * Design Pattern: Strategy Pattern
 * Role: Concrete Strategy - Provides Biz-specific movement validation.
 *
 * @author Tai Zhi Xuan
 */
public class BizMovement implements MovementStrategy {
    /**
     * Validates whether moving from one position to another is permissible
     * based on Biz's movement rules.
     *
     * Biz moves in an L-shape similar to a knight in traditional chess:
     * two squares in one direction and then one square perpendicular.
     * It can jump over other pieces.
     *
     * @param from  The current position of the Biz piece.
     * @param to    The target position to move to.
     * @param board The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        // Prevent moving to the same position
        if (from.equals(to)) return false;

        // Calculate the differences in rows and columns
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());

        // Determine if the move follows an L-shape
        boolean isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLShape) return false;

        Piece mover = board.getPieceAt(from);
        Piece occupant = board.getPieceAt(to);

        // Biz can move to an empty square or capture an opponent's piece
        return occupant == null || occupant.getColor() != mover.getColor();
    }
}
