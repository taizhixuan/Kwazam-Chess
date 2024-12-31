// PieceState.java
package model;

import java.io.Serializable;

public class PieceState implements Serializable {
    private static final long serialVersionUID = 1L;  // For version control in serialization

    String type;      // Type of piece ("Ram", "Biz", "Tor", etc.)
    int x, y;         // Position on the board
    boolean isBlue;   // True if the piece belongs to the blue player

    // Constructor for initializing a piece
    public PieceState(String type, int x, int y, boolean isBlue) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.isBlue = isBlue;
    }

    // Getters and setters for each field (if needed)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBlue() {
        return isBlue;
    }

    public void setBlue(boolean blue) {
        isBlue = blue;
    }
}
