package model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * The PieceFactory class is responsible for creating instances of different chess pieces.
 * It encapsulates the creation logic, allowing for easy addition of new piece types.
 *
 * Design Pattern:Factory Pattern
 * Role: Factory - Provides a centralized point for creating objects without exposing the creation logic.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting
 */
public class PieceFactory {
    /**
     * A registry mapping piece type strings to their corresponding constructors.
     */
    private static final Map<String, BiFunction<Color, Integer, Piece>> pieceRegistry = new HashMap<>();

    // Static block to initialize the registry with available piece types
    static {
        pieceRegistry.put("ram", Ram::new);
        pieceRegistry.put("tor", Tor::new);
        pieceRegistry.put("xor", Xor::new);
        pieceRegistry.put("biz", Biz::new);
        pieceRegistry.put("sau", Sau::new);
    }

    /**
     * Creates a piece of the specified type, color, and unique ID.
     *
     * @param type  The type of the piece (e.g., "ram", "tor").
     * @param color The color of the piece (RED or BLUE).
     * @param id    The unique identifier for the piece.
     * @return The created Piece instance.
     * @throws IllegalArgumentException If the piece type is unknown or not registered.
     *
     * @author Tai Zhi Xuan
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
     * Creates a piece of the specified type and color, automatically assigning a unique ID.
     *
     * @param type  The type of the piece (e.g., "ram", "tor").
     * @param color The color of the piece (RED or BLUE).
     * @return The created Piece instance.
     * @throws IllegalArgumentException If the piece type is unknown or not registered.
     *
     * @author Tiffany Jong Shu Ting
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
