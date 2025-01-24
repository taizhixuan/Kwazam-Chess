package model;

/**
 * The XorMovement class defines the movement logic specific to the Xor piece.
 *
 * Description:
 * Xor can move any number of squares diagonally, similar to the Bishop in traditional chess.
 * It cannot jump over other pieces. The move is valid if the path to the target position is clear
 * and the move adheres to the movement rules.
 *
 * Design Patterns:
 * - Strategy Pattern: Implements the MovementStrategy interface to encapsulate Xor's movement behavior.
 *
 * @author Tai Zhi Xuan
 */
public class XorMovement implements MovementStrategy {
    /**
     * Validates whether the Xor can move from the 'from' position to the 'to' position on the given board.
     *
     * @param from  The current position of the Xor.
     * @param to    The target position to move to.
     * @param board The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
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
                return false;
            }
            curRow += stepRow;
            curCol += stepCol;
        }

        Piece mover = board.getPieceAt(from);
        Piece occupant = board.getPieceAt(to);
        if (occupant == null) return true;
        return occupant.getColor() != mover.getColor();
    }
}
