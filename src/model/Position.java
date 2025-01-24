package model;

import java.io.Serializable;

/**
 * The Position class represents a specific location on the Kwazam Chess board.
 * It encapsulates row and column indices and provides utility methods for position management.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization compatibility

    /**
     * The row index of the position on the board.
     */
    private int row; // X-coordinate (row)

    /**
     * The column index of the position on the board.
     */
    private int column; // Y-coordinate (column)

    /**
     * Constructs a new Position with the specified row and column.
     *
     * @param row    The row index.
     * @param column The column index.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retrieves the row index of the position.
     *
     * @return The row index.
     */
    public int getRow() { return row; }

    /**
     * Retrieves the column index of the position.
     *
     * @return The column index.
     */
    public int getColumn() { return column; }

    /**
     * Sets the row index of the position.
     *
     * @param row The new row index.
     */
    public void setRow(int row) { this.row = row; }

    /**
     * Sets the column index of the position.
     *
     * @param column The new column index.
     */
    public void setColumn(int column) { this.column = column; }

    /**
     * Checks if the position is within the bounds of the board.
     *
     * @param maxRows    The maximum number of rows on the board.
     * @param maxColumns The maximum number of columns on the board.
     * @return True if the position is within bounds; false otherwise.
     */
    public boolean isWithinBounds(int maxRows, int maxColumns) {
        return row >= 0 && row < maxRows && column >= 0 && column < maxColumns;
    }

    /**
     * Determines whether two positions are equal based on their row and column.
     *
     * @param o The object to compare with.
     * @return True if both positions have the same row and column; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position pos = (Position) o;
        return row == pos.row && column == pos.column;
    }

    /**
     * Generates a hash code based on the row and column.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return 31 * row + column;
    }

    /**
     * Returns a string representation of the position in the format "(row, column)".
     *
     * @return The string representation of the position.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
