// Sau.java
package model;

/**
 * Sau: moves 1 step in any direction. Its capture ends the game.
 */
public class Sau extends Piece {
    public Sau(Color color) {
        super(color);
        this.movementStrategy = new SauMovement();
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate the validation logic to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    @Override
    public void transform(Board board) {
        // Sau doesn't transform
    }

    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Sau_red.png"
                : "resources/images/Sau_blue.png";
    }
}
