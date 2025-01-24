package model;

/**
 * The Tor class represents the Tor piece in Kwazam Chess.
 *
 * Description:
 * Tor moves like a Rook in traditional chess, moving any number of squares
 * horizontally or vertically without jumping over other pieces.
 * After making two moves, the Tor transforms into an Xor.
 *
 * Design Patterns:
 * - Strategy Pattern: Utilizes TorMovement to define its movement behavior.
 * - Template Method Pattern: Provides a framework for transformation after a certain number of moves.
 *
 * @author Tai Zhi Xuan
 */
public class Tor extends Piece {
    /**
     * Constructs a new Tor with the specified color and unique ID.
     *
     * @param color The color of the Tor (RED or BLUE).
     * @param torId The unique identifier for the Tor.
     */
    public Tor(Color color, int torId) {
        super(color, torId);
        this.movementStrategy = new TorMovement(); // Assign TorMovement strategy
    }

    /**
     * Determines if moving to the specified position is valid.
     * Delegates the validation to the TorMovement strategy.
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
     * Transforms the Tor into an Xor after two moves.
     *
     * @param board The current state of the game board.
     */
    @Override
    public void transform(Board board) {
        if (moveCount == 2) {
            Piece newXor = PieceFactory.createPiece("xor", this.color, IDGenerator.getInstance().getXorId());
            newXor.setPosition(this.position);
            newXor.setMoveCount(0);
            board.setPieceAt(this.position, newXor);
        }
    }

    /**
     * Retrieves the file path to the Tor's image based on its color.
     *
     * @return The image path as a String.
     */
    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Tor_red.png"
                : "resources/images/Tor_blue.png";
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The string "Tor".
     */
    @Override
    public String getType() {
        return "Tor";
    }

    /**
     * Sets the move count for the Tor.
     * This method is used to reset or adjust the move count when transforming.
     *
     * @param m The new move count value.
     */
    public void setMoveCount(int m) {
        this.moveCount = m;
    }
}

