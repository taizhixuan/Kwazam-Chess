package model;

import java.io.Serializable;

/**
 * The PieceState class represents the state of a chess piece at a specific point in time.
 * It is used for saving and loading game states, allowing for serialization of piece information.
 *
 * Design Patterns:
 * - Memento Pattern:Acts as a Memento by capturing the state of a piece.
 *
 * @author Joyce Ong Pay Teng
 */
public class PieceState implements Serializable {
    private static final long serialVersionUID = 1L;  // For version control in serialization

    /**
     * The type of the piece (e.g., "Ram", "Biz").
     */
    String type;      // Type of piece ("Ram", "Biz", "Tor", etc.)

    /**
     * The row position of the piece on the board.
     */
    private int x;            // X-coordinate (row)

    /**
     * The column position of the piece on the board.
     */
    private int y;            // Y-coordinate (column)

    /**
     * Indicates whether the piece belongs to the blue player.
     */
    boolean isBlue;

    /**
     * Constructs a new PieceState with the specified parameters.
     *
     * @param type    The type of the piece.
     * @param x       The row position of the piece.
     * @param y       The column position of the piece.
     * @param isBlue  True if the piece belongs to the blue player; false otherwise.
     */
    public PieceState(String type, int x, int y, boolean isBlue) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.isBlue = isBlue;
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return The piece type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the piece.
     *
     * @param type The new piece type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the row position of the piece.
     *
     * @return The row position.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the row position of the piece.
     *
     * @param x The new row position.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retrieves the column position of the piece.
     *
     * @return The column position.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the column position of the piece.
     *
     * @param y The new column position.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks whether the piece belongs to the blue player.
     *
     * @return True if the piece is blue; false otherwise.
     */
    public boolean isBlue() {
        return isBlue;
    }

    /**
     * Sets the ownership of the piece to blue or red.
     *
     * @param blue True to set the piece as blue; false for red.
     */
    public void setBlue(boolean blue) {
        isBlue = blue;
    }
}
