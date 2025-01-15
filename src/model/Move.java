// Move.java
package model;

public class Move {
    private final String player;
    private final Position from;
    private final Position to;

    public Move(String player, Position from, Position to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public String getPlayer() {
        return player;
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
                "%s: (%d,%d) -> (%d,%d)",
                player, from.getRow(), from.getColumn(),
                to.getRow(), to.getColumn()
        );
    }
}
