package model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Factory class for creating different types of chess pieces.
 */
public class PieceFactory {
    private static final Map<String, BiFunction<Color, Integer, Piece>> pieceRegistry = new HashMap<>();

    static {
        pieceRegistry.put("ram", Ram::new);
        pieceRegistry.put("tor", Tor::new);
        pieceRegistry.put("xor", Xor::new);
        pieceRegistry.put("biz", Biz::new);
        pieceRegistry.put("sau", Sau::new);
    }

    /**
     * Creates a piece with a specified type, color, and ID.
     *
     * @param type  The type of the piece (e.g., "ram", "tor").
     * @param color The color of the piece (RED or BLUE).
     * @param id    The unique ID of the piece.
     * @return The created Piece instance.
     */
    public static Piece createPiece(String type, Color color, int id) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Piece type cannot be null or empty.");
        }
        BiFunction<Color, Integer, Piece> constructor = pieceRegistry.get(type.toLowerCase());
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown piece type: " + type);
        }
        return constructor.apply(color, id);
    }

    /**
     * Creates a piece with a specified type and color, automatically assigning a unique ID.
     *
     * @param type  The type of the piece (e.g., "ram", "tor").
     * @param color The color of the piece (RED or BLUE).
     * @return The created Piece instance.
     */
    public static Piece createPiece(String type, Color color) {
        IDGenerator idGen = IDGenerator.getInstance();
        int id = switch (type.toLowerCase()) {
            case "ram" -> idGen.getNextRamId();
            case "tor" -> idGen.getTorId();
            case "xor" -> idGen.getXorId();
            case "biz" -> idGen.getNextBizId();
            case "sau" -> idGen.getSauId();
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
        return createPiece(type, color, id);
    }
}
