// BizMovement.java
package model;

/**
 * L-shape (knight-like). Jumps over pieces.
 */
public class BizMovement implements MovementStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        if (from.equals(to)) return false;

        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());

        // Knight-like pattern: (2,1) or (1,2)
        boolean isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLShape) return false;

        Piece mover = board.getPieceAt(from);
        Piece occupant = board.getPieceAt(to);
        // Jumping allowed, so no path check
        // Either empty or capturing opposite color
        return occupant == null || occupant.getColor() != mover.getColor();
    }
}
