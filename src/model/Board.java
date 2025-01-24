package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class represents the chess board in Kwazam Chess.
 * It manages the placement of pieces, handles board state updates,
 * and provides utility methods for interacting with the board.
 *
 * Design Pattern: Singleton Pattern (for IDGenerator)
 * Role: Interacts with Singleton for unique ID generation.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
 */
public class Board {
    /**
     * A 2D array representing the grid of the board.
     * Each cell can hold a Piece or be null if empty.
     */
    private final Piece[][] grid;

    /**
     * The number of rows on the board.
     */
    private static final int ROWS = 8;

    /**
     * The number of columns on the board.
     */
    private static final int COLUMNS = 5;

    /**
     * Constructs a new Board and initializes it with pieces in their starting positions.
     */
    public Board() {
        grid = new Piece[ROWS][COLUMNS];
        initializeBoard();
    }

    /**
     * Retrieves the piece located at the specified position.
     *
     * @param position The position to query.
     * @return The Piece at the given position, or null if empty.
     * @throws IllegalArgumentException If the position is invalid or null.
     */
    public Piece getPieceAt(Position position) {
        if (position == null || !position.isWithinBounds(ROWS, COLUMNS)) {
            throw new IllegalArgumentException("Invalid or null position: " + position);
        }
        return grid[position.getRow()][position.getColumn()];
    }

    /**
     * Places a piece at the specified position on the board.
     *
     * @param position The target position.
     * @param piece    The piece to place. If null, the position is cleared.
     * @throws IllegalArgumentException If the position is invalid or null.
     */
    public void setPieceAt(Position position, Piece piece) {
        if (position == null || !position.isWithinBounds(ROWS, COLUMNS)) {
            throw new IllegalArgumentException("Invalid or null position: " + position);
        }
        grid[position.getRow()][position.getColumn()] = piece;
        if (piece != null) {
            piece.setPosition(position); // Set the position of the piece
        }
    }

    /**
     * Removes any piece present at the specified position.
     *
     * @param position The position to clear.
     * @throws IllegalArgumentException If the position is invalid or null.
     */
    public void removePiece(Position position) {
        if (position == null || !position.isWithinBounds(ROWS, COLUMNS)) {
            throw new IllegalArgumentException("Invalid or null position: " + position);
        }
        grid[position.getRow()][position.getColumn()] = null;
    }

    /**
     * Checks if the specified position on the board is empty.
     *
     * @param position The position to check.
     * @return True if the position is empty; false otherwise.
     * @throws IllegalArgumentException If the position is invalid or null.
     */
    public boolean isPositionEmpty(Position position) {
        return getPieceAt(position) == null;
    }

    /**
     * Retrieves the number of rows on the board.
     *
     * @return The number of rows.
     */
    public int getRows() {
        return ROWS;
    }

    /**
     * Retrieves the number of columns on the board.
     *
     * @return The number of columns.
     */
    public int getColumns() {
        return COLUMNS;
    }

    /**
     * Retrieves a list of all pieces currently placed on the board.
     *
     * @return A List of Piece objects on the board.
     */
    public List<Piece> getPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Piece piece = grid[row][col];
                if (piece != null) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    /**
     * Clears the entire board by removing all pieces.
     */
    public void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                grid[row][col] = null;
            }
        }
    }

    /**
     * Rotates coordinates based on the player's perspective.
     * Useful for displaying the board from different player viewpoints.
     *
     * @param position  The original position.
     * @param isRedTurn True if it's Red's turn; false otherwise.
     * @return The rotated position based on the player's perspective.
     */
    public Position rotateCoordinates(Position position, boolean isRedTurn) {
        int row = position.getRow();
        int col = position.getColumn();

        if (isRedTurn) {
            row = 7 - row;
            col = 4 - col;
        }
        return new Position(row, col);
    }

    /**
     * Determines if the game is over. The game is considered over if either player
     * has no pieces left on the board.
     *
     * @return True if the game is over; false otherwise.
     */
    public boolean isGameOver() {
        boolean redHasPieces = false;
        boolean blueHasPieces = false;

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                Piece piece = getPieceAt(new Position(row, col));
                if (piece != null) {
                    if (piece.getColor() == Color.RED) {
                        redHasPieces = true;
                    } else if (piece.getColor() == Color.BLUE) {
                        blueHasPieces = true;
                    }
                }
            }
        }
        return !redHasPieces || !blueHasPieces;
    }

    /**
     * Initializes the board with pieces in their standard starting positions.
     * This method uses the PieceFactory to create instances of each piece type.
     */
    private void initializeBoard() {
        IDGenerator idGen = IDGenerator.getInstance(); // Singleton for unique ID generation

        setPieceAt(new Position(0, 0), PieceFactory.createPiece("tor", Color.RED, idGen.getTorId()));
        setPieceAt(new Position(0, 1), PieceFactory.createPiece("biz", Color.RED, idGen.getNextBizId()));
        setPieceAt(new Position(0, 2), PieceFactory.createPiece("sau", Color.RED, idGen.getSauId()));
        setPieceAt(new Position(0, 3), PieceFactory.createPiece("biz", Color.RED, idGen.getNextBizId()));
        setPieceAt(new Position(0, 4), PieceFactory.createPiece("xor", Color.RED, idGen.getXorId()));

        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(1, col), PieceFactory.createPiece("ram", Color.RED, idGen.getNextRamId()));
        }

        for (int col = 0; col < COLUMNS; col++) {
            setPieceAt(new Position(6, col), PieceFactory.createPiece("ram", Color.BLUE, idGen.getNextRamId()));
        }

        setPieceAt(new Position(7, 0), PieceFactory.createPiece("xor", Color.BLUE, idGen.getXorId()));
        setPieceAt(new Position(7, 1), PieceFactory.createPiece("biz", Color.BLUE, idGen.getNextBizId()));
        setPieceAt(new Position(7, 2), PieceFactory.createPiece("sau", Color.BLUE, idGen.getSauId()));
        setPieceAt(new Position(7, 3), PieceFactory.createPiece("biz", Color.BLUE, idGen.getNextBizId()));
        setPieceAt(new Position(7, 4), PieceFactory.createPiece("tor", Color.BLUE, idGen.getTorId()));
    }

}
