package model;

public class PieceFactory {
    public static Piece createPiece(String type, Color color, int index) {
        return switch (type.toLowerCase()) {
            case "ram" -> new Ram(color, IDGenerator.getRamId(index));
            case "tor" -> new Tor(color, IDGenerator.getTorId());
            case "xor" -> new Xor(color, IDGenerator.getXorId());
            case "biz" -> new Biz(color, IDGenerator.getBizId(index));
            case "sau" -> new Sau(color, IDGenerator.getSauId());
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}

