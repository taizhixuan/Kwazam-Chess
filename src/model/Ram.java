// Ram.java
package model;

/**
 * Ram moves 1 step forward (for Red, that’s down; for Blue, up).
 * Flips direction if it reaches the opposite edge.
 */
public class Ram extends Piece {
    private boolean goingForward; // For Red, forward means row+1; for Blue, row-1.

    public Ram(Color color) {
        super(color);
        this.movementStrategy = new RamMovement();
        // Default: Red goes downward, Blue goes upward
        this.goingForward = true;
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate validation to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    @Override
    public void onMove(Board board) {
        super.onMove(board);
        // Check row; if it's at 0 or 7, flip direction
        int r = this.position.getRow();
        if (color == Color.RED) {
            if (r == 7) {
                goingForward = false;
            } else if (r == 0) {
                goingForward = true;
            }
        } else { // BLUE
            if (r == 0) {
                goingForward = false;
            } else if (r == 7) {
                goingForward = true;
            }
        }
    }

    @Override
    public void transform(Board board) {
        // Ram does not transform into anything else
    }

    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Ram_red.png"
                : "resources/images/Ram_blue.png";
    }

    @Override
    public String getType() {
        return "Ram";  // Piece type
    }

    public boolean isGoingForward() {
        return goingForward;
    }

    public void setGoingForward(boolean forward) {
        this.goingForward = forward;
    }
}
