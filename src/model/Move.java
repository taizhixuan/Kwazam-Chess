// Move.java
package model;

public class Move {
    private final String player;
    private final String pieceType; // New field for piece name
    private final Position from;
    private final Position to;

    public Move(String player, String pieceType, Position from, Position to) {
        this.player = player;
        this.pieceType = pieceType;
        this.from = from;
        this.to = to;
    }

    public String getPlayer() {
        return player;
    }

    public String getPieceType() {
        return pieceType;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    @Override
    public String toString() {
        return String.format(
                "%s (%s): (%d,%d) -> (%d,%d)",
                player, pieceType,
                from.getRow(), from.getColumn(),
                to.getRow(), to.getColumn()
        );
    }
}
