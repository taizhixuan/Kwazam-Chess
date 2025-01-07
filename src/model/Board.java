// Board.java
package model;

public class Board {
    private final Piece[][] grid;
    private static final int ROWS = 8;
    private static final int COLUMNS = 5;


    public Board() {
        grid = new Piece[ROWS][COLUMNS];
        initializeBoard();
    }

    public Piece getPieceAt(Position position) {
        if (position == null || !position.isWithinBounds(ROWS, COLUMNS)) {
            throw new IllegalArgumentException("Invalid or null position: " + position);
        }
        return grid[position.getRow()][position.getColumn()];
    }

    public void setPieceAt(Position position, Piece piece) {
        if (position == null || !position.isWithinBounds(ROWS, COLUMNS)) {
            throw new IllegalArgumentException("Invalid or null position: " + position);
        }
        grid[position.getRow()][position.getColumn()] = piece;
        if (piece != null) {
            piece.setPosition(position); // Set the position of the piece
        }
    }

    public void removePiece(Position position) {
        if (position == null || !position.isWithinBounds(ROWS, COLUMNS)) {
            throw new IllegalArgumentException("Invalid or null position: " + position);
        }
        grid[position.getRow()][position.getColumn()] = null;
    }

    public boolean isPositionEmpty(Position position) {
        return getPieceAt(position) == null;
    }

    public int getRows() { return ROWS; }

    public int getColumns() { return COLUMNS; }


    // Assigned IDs for each different type of pieces
    public static class IDGenerator {
        private static int ramId = 1;
        private static final int torId = 7;
        private static final int xorId = 6;
        private static int bizId = 8;
        private static final int sauId = 10;

        public static int getNextRamId() {
            return ramId++;
        }

        public static int getTorId() {
            return torId;
        }

        public static int getXorId() {
            return xorId;
        }

        public static int getNextBizId() {
            return bizId++;
        }

        public static int getSauId() {
            return sauId;
        }
    }

    private void initializeBoard() {
        // Top row - Red
        setPieceAt(new Position(0, 0), new Tor(Color.RED, IDGenerator.getTorId()));
        setPieceAt(new Position(0, 1), new Biz(Color.RED, IDGenerator.getNextBizId()));
        setPieceAt(new Position(0, 2), new Sau(Color.RED, IDGenerator.getSauId()));
        setPieceAt(new Position(0, 3), new Biz(Color.RED, IDGenerator.getNextBizId()));
        setPieceAt(new Position(0, 4), new Xor(Color.RED, IDGenerator.getXorId()));

        // Row 1 - Red Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(1, col), new Ram(Color.RED, IDGenerator.getNextRamId()));
        }

        // Row 6 - Blue Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(6, col), new Ram(Color.BLUE, IDGenerator.getNextRamId()));
        }

        // Bottom row - Blue
        setPieceAt(new Position(7, 0), new Xor(Color.BLUE, IDGenerator.getXorId()));
        setPieceAt(new Position(7, 1), new Biz(Color.BLUE, IDGenerator.getNextBizId()));
        setPieceAt(new Position(7, 2), new Sau(Color.BLUE,IDGenerator.getSauId()));
        setPieceAt(new Position(7, 3), new Biz(Color.BLUE, IDGenerator.getNextBizId()));
        setPieceAt(new Position(7, 4), new Tor(Color.BLUE, IDGenerator.getTorId()));
    }
}
