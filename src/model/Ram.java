package model;

/**
 * The Ram class represents the Ram piece in Kwazam Chess.
 * Description:
 * Ram moves one step forward based on its color:
 * If the Ram reaches the opposite edge of the board, it flips its direction.
 *
 * Design Patterns:
 * - Strategy Pattern: Utilizes RamMovement to define its movement behavior.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
 */
public class Ram extends Piece {
    /**
     * Indicates the current moving direction of the Ram.
     * For Red, forward means row+1; for Blue, row-1.
     */
    private boolean goingForward;

    /**
     * Constructs a new Ram with the specified color and unique ID.
     *
     * @param color The color of the Ram (RED or BLUE).
     * @param ramId The unique identifier for the Ram.
     */
    public Ram(Color color, int ramId) {
        super(color, ramId);
        this.movementStrategy = new RamMovement(); // Assign RamMovement strategy
        this.goingForward = true; // Default direction based on color
    }

    /**
     * Determines if moving to the specified position is valid.
     * Delegates the validation to the RamMovement strategy.
     *
     * @param newPosition The target position to move to.
     * @param board       The current state of the game board.
     * @return True if the move is valid; false otherwise.
     */
    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    /**
     * Handles actions after the Ram has been moved.
     * Specifically, it checks if the Ram has reached the opposite edge and flips its direction if necessary.
     *
     * @param board The current state of the game board.
     */
    @Override
    public void onMove(Board board) {
        super.onMove(board); // Increment move count and check for transformation
        int currentRow = this.position.getRow();

        if (color == Color.RED) {
            if (currentRow == board.getRows() - 1) { // Reached bottom edge
                goingForward = false; // Flip direction to upward
            } else if (currentRow == 0) { // Reached top edge
                goingForward = true; // Flip direction to downward
            }
        } else { // Color.BLUE
            if (currentRow == 0) { // Reached top edge
                goingForward = false; // Flip direction to downward
            } else if (currentRow == board.getRows() - 1) { // Reached bottom edge
                goingForward = true; // Flip direction to upward
            }
        }
    }

    /**
     * Ram does not transform into any other piece.
     * This method is overridden to provide no transformation behavior.
     *
     * @param board The current state of the game board.
     */
    @Override
    public void transform(Board board) {
        // Ram does not transform into anything else
    }

    /**
     * Retrieves the file path to the Ram's image based on its color.
     *
     * @return The image path as a String.
     */
    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Ram_red.png"
                : "resources/images/Ram_blue.png";
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The string "Ram".
     */
    @Override
    public String getType() {
        return "Ram";
    }

    /**
     * Checks if the Ram is currently moving forward.
     *
     * @return True if moving forward; false otherwise.
     */
    public boolean isGoingForward() {
        return goingForward;
    }

    /**
     * Sets the Ram's moving direction.
     *
     * @param forward True to set direction as forward; false for backward.
     */
    public void setGoingForward(boolean forward) {
        this.goingForward = forward;
    }
}
