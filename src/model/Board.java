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


    /**
     *  Place all pieces at their initial place.
     */
    private void initializeBoard() {
        // Top row - Red
        setPieceAt(new Position(0, 0), PieceFactory.createPiece("tor", Color.RED, 0));
        setPieceAt(new Position(0, 1), PieceFactory.createPiece("biz", Color.RED, 0));
        setPieceAt(new Position(0, 2), PieceFactory.createPiece("sau", Color.RED, 0));
        setPieceAt(new Position(0, 3), PieceFactory.createPiece("biz", Color.RED, 1));
        setPieceAt(new Position(0, 4), PieceFactory.createPiece("xor", Color.RED, 0));

        // Row 1 - Red Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(1, col), PieceFactory.createPiece("ram", Color.RED, col));
        }

        // Row 6 - Blue Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(6, col), PieceFactory.createPiece("ram", Color.BLUE, col));
        }

        // Bottom row - Blue
        setPieceAt(new Position(7, 0), PieceFactory.createPiece("xor", Color.BLUE, 0));
        setPieceAt(new Position(7, 1), PieceFactory.createPiece("biz", Color.BLUE, 0));
        setPieceAt(new Position(7, 2), PieceFactory.createPiece("sau", Color.BLUE, 0));
        setPieceAt(new Position(7, 3), PieceFactory.createPiece("biz", Color.BLUE, 1));
        setPieceAt(new Position(7, 4), PieceFactory.createPiece("tor", Color.BLUE, 0));
    }

}
