package model;

/**
 * The Biz class represents the Biz piece in Kwazam Chess.
 * It extends the abstract Piece class and defines specific behaviors
 * and properties unique to the Biz piece.
 *
 * Design Pattern: Strategy Pattern
 * Role: Concrete Strategy - Implements specific movement logic for the Biz piece.
 *
 * Design Pattern: Factory Pattern
 * Role: Product - Created by the PieceFactory to encapsulate Biz-specific creation logic.
 *
 * @author Tai Zhi Xuan
 */
public class Biz extends Piece {
    /**
     * Constructs a new Biz piece with the specified color and unique ID.
     *
     * @param color The color of the piece (RED or BLUE).
     * @param bizId The unique identifier for this Biz piece.
     */
    public Biz(Color color, int bizId) {
        super(color, bizId);
        this.movementStrategy = new BizMovement();
    }

    /**
     * Determines if moving to the specified position is valid.
     * Delegates the validation logic to the assigned MovementStrategy.
     *
     * @param newPosition The target position to move to.
     * @param board       The current state of the game board.
     * @return True if the move is valid according to Biz's movement rules; false otherwise.
     */
    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate move validation logic to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    /**
     * Transforms the Biz piece. In this implementation, Biz does not transform,
     * so this method remains empty.
     *
     * Design Pattern: Template Method Pattern
     * Role: Primitive Operation - Biz does not require transformation.
     *
     * @param board The current state of the game board.
     */
    @Override
    public void transform(Board board) {
        // Biz does not transform
    }

    /**
     * Retrieves the file path to the Biz piece's image based on its color.
     *
     * @return The image path as a String.
     */
    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Biz_red.png"
                : "resources/images/Biz_blue.png";
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The type of the piece as a String.
     */
    @Override
    public String getType() {
        return "Biz";
    }
}
