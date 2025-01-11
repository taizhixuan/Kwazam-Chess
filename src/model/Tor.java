// Tor.java
package model;

/**
 * Tor: rook-like movement, transforms into Xor after 2 moves.
 */
public class Tor extends Piece {
    public Tor(Color color, int torId) {
        super(color, torId);
        this.movementStrategy = new TorMovement(); // Initialize movement strategy
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate validation to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    @Override
    public void transform(Board board) {
        if (moveCount == 2) {
            // Replace this Tor with a new Xor
            int xorId = IDGenerator.getXorId();
            Xor newXor = new Xor(this.color, xorId);
            newXor.setPosition(this.position);
            newXor.setMoveCount(0); // Reset move count for the transformed piece
            board.setPieceAt(this.position, newXor);
        }
    }

    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Tor_red.png"
                : "resources/images/Tor_blue.png";
    }

    @Override
    public String getType() {
        return "Tor";  // Piece type
    }

    public void setMoveCount(int m) {
        this.moveCount = m;
    }
}

