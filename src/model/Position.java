// Position.java
package model;

import java.io.Serializable;

/**
 * Represents a row/column coordinate on the Kwazam Chess board.
 */
public class Position implements Serializable {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }

    public void setRow(int row) { this.row = row; }
    public void setColumn(int column) { this.column = column; }

    public boolean isWithinBounds(int maxRows, int maxColumns) {
        return row >= 0 && row < maxRows && column >= 0 && column < maxColumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position pos = (Position) o;
        return row == pos.row && column == pos.column;
    }

    @Override
    public int hashCode() {
        return 31 * row + column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
