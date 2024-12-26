// TorMovement.java
package model;

/**
 * Tor: moves like a Rook (orthogonally). No jumping over pieces.
 */
public class TorMovement implements MovementStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        if (from.equals(to)) return false;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getColumn() - from.getColumn();
        boolean sameRow = (rowDiff == 0 && colDiff != 0);
        boolean sameCol = (colDiff == 0 && rowDiff != 0);

        if (!sameRow && !sameCol) return false;

        int stepRow = Integer.signum(rowDiff);
        int stepCol = Integer.signum(colDiff);

        int curRow = from.getRow() + stepRow;
        int curCol = from.getColumn() + stepCol;
        while (curRow != to.getRow() || curCol != to.getColumn()) {
            if (!board.isPositionEmpty(new Position(curRow, curCol))) {
                return false; // blocked
            }
            curRow += stepRow;
            curCol += stepCol;
        }

        // final square occupant
        Piece mover = board.getPieceAt(from);
        Piece occupant = board.getPieceAt(to);
        if (occupant == null) return true;
        return occupant.getColor() != mover.getColor();
    }
}
