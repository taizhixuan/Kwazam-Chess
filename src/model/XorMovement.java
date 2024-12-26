// XorMovement.java
package model;

/**
 * Xor: bishop-like diagonal moves, no jumping over pieces.
 */
public class XorMovement implements MovementStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        if (from.equals(to)) return false;

        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getColumn() - from.getColumn());
        if (rowDiff != colDiff) return false; // must be diagonal

        int stepRow = Integer.signum(to.getRow() - from.getRow());
        int stepCol = Integer.signum(to.getColumn() - from.getColumn());

        int curRow = from.getRow() + stepRow;
        int curCol = from.getColumn() + stepCol;
        while (curRow != to.getRow() || curCol != to.getColumn()) {
            if (!board.isPositionEmpty(new Position(curRow, curCol))) {
                return false; // blocked
            }
            curRow += stepRow;
            curCol += stepCol;
        }

        // final occupant
        Piece mover = board.getPieceAt(from);
        Piece occupant = board.getPieceAt(to);
        if (occupant == null) return true;
        return occupant.getColor() != mover.getColor();
    }
}
