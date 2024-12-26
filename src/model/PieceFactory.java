// PieceFactory.java
package model;

public class PieceFactory {
    public static Piece createPiece(String type, Color color) {
        return switch (type.toLowerCase()) {
            case "ram" -> new Ram(color);
            case "tor" -> new Tor(color);
            case "xor" -> new Xor(color);
            case "biz" -> new Biz(color);
            case "sau" -> new Sau(color);
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}

