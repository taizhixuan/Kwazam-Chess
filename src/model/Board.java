// Board.java
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chess board and manages the placement of pieces.
 */
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
     *
     * @return List of all pieces on the board.
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
     *
     * @param position The original position.
     * @param isRedTurn True if it's Red's turn, false otherwise.
     * @return The rotated position based on the player's perspective.
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

    /**
     * Determines if the game is over. The game is over if either player has no pieces left.
     *
     * @return True if the game is over, false otherwise.
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

    /**
     * Initializes the board with starting positions for all pieces.
     */
    private void initializeBoard() {
        IDGenerator idGen = IDGenerator.getInstance();

        // Top row - Red
        setPieceAt(new Position(0, 0), new Tor(Color.RED, idGen.getTorId()));
        setPieceAt(new Position(0, 1), new Biz(Color.RED, idGen.getNextBizId()));
        setPieceAt(new Position(0, 2), new Sau(Color.RED, idGen.getSauId()));
        setPieceAt(new Position(0, 3), new Biz(Color.RED, idGen.getNextBizId()));
        setPieceAt(new Position(0, 4), new Xor(Color.RED, idGen.getXorId()));

        // Row 1 - Red Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(1, col), new Ram(Color.RED, idGen.getNextRamId()));
        }

        // Row 6 - Blue Rams
        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(6, col), new Ram(Color.BLUE, idGen.getNextRamId()));
        }

        // Bottom row - Blue
        setPieceAt(new Position(7, 0), new Xor(Color.BLUE, idGen.getXorId()));
        setPieceAt(new Position(7, 1), new Biz(Color.BLUE, idGen.getNextBizId()));
        setPieceAt(new Position(7, 2), new Sau(Color.BLUE, idGen.getSauId()));
        setPieceAt(new Position(7, 3), new Biz(Color.BLUE, idGen.getNextBizId()));
        setPieceAt(new Position(7, 4), new Tor(Color.BLUE, idGen.getTorId()));
    }
}
