// PieceFactory.java
package model;

/**
 * Factory class for creating different types of chess pieces.
 */
public class PieceFactory {
    /**
     * Creates a piece with a specified type, color, and ID.
     *
     * @param type The type of the piece (e.g., "ram", "tor").
     * @param color The color of the piece (RED or BLUE).
     * @param id The unique ID of the piece.
     * @return The created Piece instance.
     */
    public static Piece createPiece(String type, Color color, int id) {
        return switch (type.toLowerCase()) {
            case "ram" -> new Ram(color, id);
            case "tor" -> new Tor(color, id);
            case "xor" -> new Xor(color, id);
            case "biz" -> new Biz(color, id);
            case "sau" -> new Sau(color, id);
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }

    /**
     * Creates a piece with a specified type and color, automatically assigning a unique ID.
     *
     * @param type The type of the piece (e.g., "ram", "tor").
     * @param color The color of the piece (RED or BLUE).
     * @return The created Piece instance.
     */
    public static Piece createPiece(String type, Color color) {
        IDGenerator idGen = IDGenerator.getInstance();
        return switch (type.toLowerCase()) {
            case "ram" -> new Ram(color, idGen.getNextRamId());
            case "tor" -> new Tor(color, idGen.getTorId());
            case "xor" -> new Xor(color, idGen.getXorId());
            case "biz" -> new Biz(color, idGen.getNextBizId());
            case "sau" -> new Sau(color, idGen.getSauId());
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}
