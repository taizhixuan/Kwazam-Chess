package model;

public class PieceFactory {
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

