package model;

/**
 * The Sau class represents the Sau piece in Kwazam Chess.
 *
 * Description:
 * Sau can move one step in any direction, including diagonals.
 * Capturing an opponent's Sau ends the game.
 *
 * Design Patterns:
 * - Strategy Pattern: Utilizes SauMovement to define its movement behavior.
 *
 * @author Joyce Ong Pay Teng
 */
public class Sau extends Piece {
    /**
     * Constructs a new Sau with the specified color and unique ID.
     *
     * @param color The color of the Sau (RED or BLUE).
     * @param sauId The unique identifier for the Sau.
     */
    public Sau(Color color, int sauId) {
        super(color, sauId);
        this.movementStrategy = new SauMovement(); // Assign SauMovement strategy
    }

    /**
     * Determines if moving to the specified position is valid.
     * Delegates the validation to the SauMovement strategy.
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
     * Sau does not transform into any other piece.
     * This method is overridden to provide no transformation behavior.
     *
     * @param board The current state of the game board.
     */
    @Override
    public void transform(Board board) {
        // Sau doesn't transform
    }

    /**
     * Retrieves the file path to the Sau's image based on its color.
     *
     * @return The image path as a String.
     */
    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Sau_red.png"
                : "resources/images/Sau_blue.png";
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The string "Sau".
     */
    @Override
    public String getType() {
        return "Sau";
    }
}
