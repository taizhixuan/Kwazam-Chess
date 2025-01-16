// Xor.java
package model;

/**
 * Xor: bishop-like, transforms back to Tor after 2 moves.
 */
public class Xor extends Piece {
    public Xor(Color color, int xorId) {
        super(color, xorId);
        this.movementStrategy = new XorMovement(); // Initialize movement strategy
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate validation to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    @Override
    public void transform(Board board) {
        if (moveCount == 2) {
            // Replace this Xor with a new Tor
            Piece newTor = PieceFactory.createPiece("tor", this.color, IDGenerator.getInstance().getTorId());
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

    @Override
    public String getType() {
        return "Xor";  // Piece type
    }

    public void setMoveCount(int m) {
        this.moveCount = m;
    }
}
