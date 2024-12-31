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

    private void initializeBoard() {
        // Top row - Red
        setPieceAt(new Position(0, 0), new Tor(Color.RED));
        setPieceAt(new Position(0, 1), new Biz(Color.RED));
        setPieceAt(new Position(0, 2), new Sau(Color.RED));
        setPieceAt(new Position(0, 3), new Biz(Color.RED));
        setPieceAt(new Position(0, 4), new Xor(Color.RED));

        // Row 1 - Red Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(1, col), new Ram(Color.RED));
        }

        // Row 6 - Blue Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(6, col), new Ram(Color.BLUE));
        }

        // Bottom row - Blue
        setPieceAt(new Position(7, 0), new Xor(Color.BLUE));
        setPieceAt(new Position(7, 1), new Biz(Color.BLUE));
        setPieceAt(new Position(7, 2), new Sau(Color.BLUE));
        setPieceAt(new Position(7, 3), new Biz(Color.BLUE));
        setPieceAt(new Position(7, 4), new Tor(Color.BLUE));
    }
}
