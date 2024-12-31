// Xor.java
package model;

/**
 * Xor: bishop-like, transforms back to Tor after 2 moves.
 */
public class Xor extends Piece {
    public Xor(Color color) {
        super(color);
        this.movementStrategy = new XorMovement(); // Initialize movement strategy
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate validation to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    @Override
    public void transform(Board board) {
        // Replace this Xor with a new Tor
        if (moveCount == 2) {
            Tor newTor = new Tor(this.color);
            newTor.setPosition(this.position);
            newTor.setMoveCount(0); // Reset move count for the transformed piece
            board.setPieceAt(this.position, newTor);
        }
    }

    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Xor_red.png"
                : "resources/images/Xor_blue.png";
    }

    public void setMoveCount(int m) {
        this.moveCount = m;
    }
}
