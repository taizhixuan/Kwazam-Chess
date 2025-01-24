package model;

/**
 * The Move class encapsulates a single move made by a player in Kwazam Chess.
 * It represents the action of moving a piece from one position to another.
 *
 * Design Pattern: Command Pattern
 * Role: Concrete Command - Encapsulates all information needed to perform an action,
 * including the action itself and its parameters.
 *
 * @author Tai Zhi Xuan
 */
public class Move {
    /**
     * The player who is making the move (e.g., "Red" or "Blue").
     */
    private final String player;

    /**
     * The type of piece being moved (e.g., "Biz", "Ram").
     */
    private final String pieceType;

    /**
     * The starting position of the piece before the move.
     */
    private final Position from;

    /**
     * The target position where the piece is moved to.
     */
    private final Position to;

    /**
     * Constructs a new Move instance with the specified parameters.
     *
     * @param player    The player making the move.
     * @param pieceType The type of piece being moved.
     * @param from      The starting position of the piece.
     * @param to        The target position of the move.
     */
    public Move(String player, String pieceType, Position from, Position to) {
        this.player = player;
        this.pieceType = pieceType;
        this.from = from;
        this.to = to;
    }

    /**
     * Retrieves the player who made the move.
     *
     * @return The player's name.
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Retrieves the type of piece that was moved.
     *
     * @return The piece type.
     */
    public String getPieceType() {
        return pieceType;
    }

    /**
     * Retrieves the starting position of the piece.
     *
     * @return The starting Position.
     */
    public Position getFrom() {
        return from;
    }

    /**
     * Retrieves the target position of the move.
     *
     * @return The target Position.
     */
    public Position getTo() {
        return to;
    }

    /**
     * Returns a string representation of the move.
     * Format: "Player (PieceType): (FromRow,FromCol) -> (ToRow,ToCol)"
     *
     * @return The string representation of the move.
     */
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
