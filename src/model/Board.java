// Board.java
package model;

import java.util.ArrayList;
import java.util.List;

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

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    /**
     * Get all pieces currently on the board.
     */
    public List<Piece> getPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    pieces.add(piece); // Add piece to the list if it's not null
                }
            }
        }
        return pieces;
    }

    /**
     * Clear the entire board by removing all pieces.
     */
    public void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                grid[row][col] = null;
            }
        }
    }

    /**
     * Rotate coordinates if it's Red's turn.
     */
    public Position rotateCoordinates(Position position, boolean isRedTurn) {
        int row = position.getRow();
        int col = position.getColumn();

        // Flip coordinates for the Red player's turn (180-degree rotation)
        if (isRedTurn) {
            row = 7 - row;
            col = 4 - col;
        }
        return new Position(row, col);
    }

    public void placePiece(Piece piece, Position position) {
        // This method can be implemented if needed for piece placement logic
    }

    /**
     * Assigned IDs for each different type of pieces
     */
    public static class IDGenerator {
        private static int ramId = 1;
        private static final int torId = 7;
        private static final int xorId = 6;
        private static int bizId = 8;
        private static final int sauId = 10;

        public static int getNextRamId() {
            if (ramId <= 5) {
                return ramId++; // Rams will get IDs from 1 to 5
            } else {
                ramId = 1; // Reset for the next round (or could throw error, depending on needs)
                return ramId++;
            }
        }

        public static int getTorId() {
            return torId;
        }

        public static int getXorId() {
            return xorId;
        }

        // Get the next available Biz ID (8 or 9)
        public static int getNextBizId() {
            if (bizId <= 9) {
                return bizId++;
            } else {
                bizId = 8; // Reset Biz ID
                return bizId++;
            }
        }

        public static int getSauId() {
            return sauId;
        }
    }

    /**
     * Place all pieces at their initial place.
     */
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
        setPieceAt(new Position(7, 2), new Sau(Color.BLUE, IDGenerator.getSauId()));
        setPieceAt(new Position(7, 3), new Biz(Color.BLUE, IDGenerator.getNextBizId()));
        setPieceAt(new Position(7, 4), new Tor(Color.BLUE, IDGenerator.getTorId()));
    }

    /**
     * Determines if the game is over. The game is over if either player has no pieces left.
     */
    public boolean isGameOver() {
        boolean redHasPieces = false;
        boolean blueHasPieces = false;

        // Iterate through all rows and columns
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                Piece piece = getPieceAt(new Position(row, col));
                if (piece != null) {
                    // Check if any Red or Blue pieces are still on the board
                    if (piece.getColor() == Color.RED) {
                        redHasPieces = true;
                    } else if (piece.getColor() == Color.BLUE) {
                        blueHasPieces = true;
                    }
                }
            }
        }

        // Game is over if either Red or Blue has no pieces left
        return !redHasPieces || !blueHasPieces;
    }
}
