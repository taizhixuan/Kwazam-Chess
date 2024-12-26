// RamMovement.java
package model;

/**
 * 1 step in the Ram's forward direction.
 */
public class RamMovement implements MovementStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Board board) {
        Piece piece = board.getPieceAt(from);
        if (!(piece instanceof Ram)) return false;

        Ram ram = (Ram) piece;
        if (from.equals(to)) return false;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getColumn() - from.getColumn();

        // If Ram is goingForward:
        //    - Red => rowDiff = +1
        //    - Blue => rowDiff = -1
        // If not goingForward:
        //    - Red => rowDiff = -1
        //    - Blue => rowDiff = +1
        int expectedRowStep = 0;
        if (ram.isGoingForward()) {
            expectedRowStep = (ram.getColor() == Color.RED) ? +1 : -1;
        } else {
            expectedRowStep = (ram.getColor() == Color.RED) ? -1 : +1;
        }

        // Must be exactly that row step and colDiff=0
        if (rowDiff == expectedRowStep && colDiff == 0) {
            Piece occupant = board.getPieceAt(to);
            // either empty or capturing opponent
            return occupant == null || occupant.getColor() != ram.getColor();
        }
        return false;
    }
}
