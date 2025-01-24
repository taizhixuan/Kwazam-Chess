package model;

/**
 * The TorMovement class defines the movement logic specific to the Tor piece.
 *
 * Description:
 * <br>Tor can move any number of squares horizontally or vertically,
 * similar to the Rook in traditional chess. It cannot jump over other pieces.
 * The move is valid if the path to the target position is clear and the move adheres
 * to the movement rules.
 *
 * Design Patterns:
 * - Strategy Pattern: Implements the MovementStrategy interface to encapsulate Tor's movement behavior.
 *
 * @author Tai Zhi Xuan
 */
public class TorMovement implements MovementStrategy {
    /**
     * Validates whether the Tor can move from the 'from' position to the 'to' position on the given board.
     *
     * @param from  The current position of the Tor.
     * @param to    The target position to move to.
     * @param board The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
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
