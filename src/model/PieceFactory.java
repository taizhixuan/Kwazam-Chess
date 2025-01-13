package model;

public class PieceFactory {
    // Overloaded method that accepts an id parameter
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

    // Original method for creating new pieces (still in use for new pieces)
    public static Piece createPiece(String type, Color color) {
        return switch (type.toLowerCase()) {
            case "ram" -> new Ram(color, Board.IDGenerator.getNextRamId());
            case "tor" -> new Tor(color, Board.IDGenerator.getTorId());
            case "xor" -> new Xor(color, Board.IDGenerator.getXorId());
            case "biz" -> new Biz(color, Board.IDGenerator.getNextBizId());
            case "sau" -> new Sau(color, Board.IDGenerator.getSauId());
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}
