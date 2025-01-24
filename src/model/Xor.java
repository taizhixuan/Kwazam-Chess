package model;

/**
 * The Xor class represents the Xor piece in Kwazam Chess.
 *
 * Description:
 * Xor moves diagonally any number of squares without jumping over other pieces,
 * similar to the Bishop in traditional chess. After making two moves, the Xor transforms back into a Tor.
 *
 * Design Patterns:
 * - Strategy Pattern: Utilizes XorMovement to define its movement behavior.
 * - Template Method Pattern: Provides a framework for transformation after a certain number of moves.
 *
 * @author Tai Zhi Xuan
 */
public class Xor extends Piece {
    /**
     * Constructs a new Xor with the specified color and unique ID.
     *
     * @param color The color of the Xor (RED or BLUE).
     * @param xorId The unique identifier for the Xor.
     */
    public Xor(Color color, int xorId) {
        super(color, xorId);
        this.movementStrategy = new XorMovement(); // Assign XorMovement strategy
    }

    /**
     * Determines if moving to the specified position is valid.
     * Delegates the validation to the XorMovement strategy.
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
     * Transforms the Xor back into a Tor after two moves.
     *
     * @param board The current state of the game board.
     */
    @Override
    public void transform(Board board) {
        if (moveCount == 2) {
            Piece newTor = PieceFactory.createPiece("tor", this.color, IDGenerator.getInstance().getTorId());
            newTor.setPosition(this.position);
            newTor.setMoveCount(0);
            board.setPieceAt(this.position, newTor);
        }
    }

    /**
     * Retrieves the file path to the Xor's image based on its color.
     *
     * @return The image path as a String.
     */
    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Xor_red.png"
                : "resources/images/Xor_blue.png";
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The string "Xor".
     */
    @Override
    public String getType() {
        return "Xor";
    }

    /**
     * Sets the move count for the Xor.
     * This method is used to reset or adjust the move count when transforming.
     *
     * @param m The new move count value.
     */
    public void setMoveCount(int m) {
        this.moveCount = m;
    }
}
