// GameController.java
package controller;

import model.*;
import view.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameController acts as the Controller in the MVC pattern.
 * It observes the Game model and updates the view accordingly.
 *
 * Part of Observer Design Pattern: Acts as an observer to the Game model.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
 */
public class GameController implements GameObserver {
    private final Game game;
    private Position selectedPiece; // Stores the currently selected piece

    private final List<Move> moveHistory = new ArrayList<>();

    private int secondsElapsed = 0;

    private final BoardView view; // Reference to the view

    /**
     * Constructor for GameController.
     *
     * @param board The initial game board.
     * @author Tai Zhi Xuan
     */
    public GameController(Board board) {
        this.game = new Game(board);
        this.view = new GameScreen(this); // Create the view, passing the controller
        this.game.addObserver(this);
    }

    /**
     * Retrieves the view associated with this controller.
     *
     * @return The BoardView instance.
     * @author Tai Zhi Xuan
     */
    public BoardView getView() {
        return view;
    }

    /**
     * Retrieves the number of seconds elapsed.
     *
     * @return The elapsed seconds.
     * @author Tai Zhi Xuan
     */
    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    /**
     * Sets the elapsed seconds.
     *
     * @param secondsElapsed The elapsed seconds to set.
     * @author Tai Zhi Xuan
     */
    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }

    /**
     * Increments the elapsed seconds by one.
     *
     * @author Tai Zhi Xuan
     */
    public void incrementSecondsElapsed() {
        this.secondsElapsed++;
    }

    /**
     * Checks if a piece is selected.
     *
     * @return True if a piece is selected, false otherwise.
     * @author Tai Zhi Xuan
     */
    public boolean isPieceSelected() {
        return selectedPiece != null;
    }

    /**
     * Retrieves the currently selected piece position.
     *
     * @return The selected piece position.
     * @author Tai Zhi Xuan
     */
    public Position getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Handles a move request from one position to another.
     *
     * @param from The starting position.
     * @param to   The target position.
     * @return True if the move was successful, false otherwise.
     * @author Tai Zhi Xuan
     */
    public boolean movePiece(Position from, Position to) {
        String currentPlayer = getCurrentPlayer();
        Piece piece = game.getBoard().getPieceAt(from);
        if (piece == null) {
            return false;
        }
        String pieceType = piece.getType();
        boolean success = game.movePiece(from, to);
        if (success) {
            Move move = new Move(currentPlayer, pieceType, from, to);
            moveHistory.add(move);
        }
        return success;
    }

    /**
     * Handles tile clicks in the GUI.
     *
     * @param position The position that was clicked.
     * @param view     The view component.
     * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
     */
    public void handleTileClick(Position position, BoardView view) {
        int clickedRow = position.getRow();
        int clickedCol = position.getColumn();
        boolean isRed = getCurrentPlayer().equals("RED");

        int displayClickedRow = isRed ? (7 - clickedRow) : clickedRow;
        int displayClickedCol = isRed ? (4 - clickedCol) : clickedCol;

        if (selectedPiece == null) {
            Piece piece = game.getBoard().getPieceAt(position);
            if (piece != null && piece.getColor().name().equalsIgnoreCase(getCurrentPlayer())) {
                selectedPiece = position;
                List<Position> validMoves = game.getPossibleMoves(piece, position);

                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: %s piece at (%d, %d) selected.%n",
                        piece.getClass().getSimpleName(),
                        displayFromRow, displayFromCol
                );

                view.clearHighlights();
                view.highlightSelectedPiece(position);
                view.highlightValidMoves(validMoves);
            } else {
                System.out.println("GUI: No valid piece selected or it's the opponent's piece.");
            }
        } else {
            if (selectedPiece.equals(position)) {
                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: Piece at (%d, %d) deselected.%n",
                        displayFromRow, displayFromCol
                );

                selectedPiece = null;
                view.clearHighlights();

            } else {
                Piece pieceAtClicked = game.getBoard().getPieceAt(position);
                if (pieceAtClicked != null &&
                        pieceAtClicked.getColor().name().equalsIgnoreCase(getCurrentPlayer())) { // Corrected line
                    System.out.println("GUI: You must deselect the current piece first.");
                    return;
                }

                int displayFromRow = isRed ? (7 - selectedPiece.getRow()) : selectedPiece.getRow();
                int displayFromCol = isRed ? (4 - selectedPiece.getColumn()) : selectedPiece.getColumn();

                System.out.printf(
                        "GUI: Attempting to move from (%d, %d) to (%d, %d).%n",
                        displayFromRow, displayFromCol,
                        displayClickedRow, displayClickedCol
                );

                if (movePiece(selectedPiece, position)) {
                    System.out.printf(
                            "GUI: Move successful: from (%d, %d) to (%d, %d).%n",
                            displayFromRow, displayFromCol,
                            displayClickedRow, displayClickedCol
                    );

                    view.clearHighlights();
                    view.refreshBoard();

                    if (game.isGameOver()) {
                        String winnerMessage = game.getWinner() + " wins! Game Over.";
                        System.out.println("GUI: " + winnerMessage);
                        view.gameOver(winnerMessage);
                    }
                } else {
                    System.out.println("GUI: Invalid move. Try again.");
                }

                selectedPiece = null;
                view.refreshBoard();
            }
        }
    }

    /**
     * This method is called whenever the Game notifies its observers.
     *
     * @param event The game event that occurred.
     * @author Tai Zhi Xuan
     */
    @Override
    public void update(GameEvent event) {
        switch (event) {
            case MOVE:
                // Refresh the board to reflect the move
                view.refreshBoard();
                break;
            case RESET:
                // Reset move history and timer
                moveHistory.clear();
                secondsElapsed = 0;
                if (view instanceof GameScreen) {
                    ((GameScreen) view).updateMoveList();
                }
                break;
            case TRANSFORM:
                // Refresh the board to reflect transformations
                view.refreshBoard();
                break;
            case GAME_OVER:
                // Handle game over scenario
                if (game.isGameOver()) {
                    String winnerMessage = game.getWinner() + " wins! Game Over.";
                    view.gameOver(winnerMessage);
                }
                break;
            default:
                break;
        }

        if (event == GameEvent.MOVE && view instanceof GameScreen) {
            ((GameScreen) view).updateMoveList();
        }
    }

    /**
     * Gets the current game board.
     *
     * @return The game board.
     * @author Tai ZHi Xuan
     */
    public Board getBoard() {
        return game.getBoard();
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     * @author Tiffany Jong Shu Ting
     */
    public boolean isGameOver() {
        return game.isGameOver();
    }

    /**
     * Determines the winner of the game.
     *
     * @return The winner's color as a String, or null if the game isn't over.
     * @author Tiffany Jong Shu Ting
     */
    public String getWinner() {
        return game.getWinner();
    }

    /**
     * Gets the current player.
     *
     * @return The current player's color as a String.
     * @author Tiffany Jong Shu Ting
     */
    public String getCurrentPlayer() {
        return game.getCurrentPlayer().name();
    }

    /**
     * Gets the move history.
     *
     * @return An unmodifiable list of moves.
     * @author Tai Zhi Xuan
     */
    public List<Move> getMoveHistory() {
        return Collections.unmodifiableList(moveHistory);
    }

    /**
     * Resets the game.
     *
     * @author Tiffany Jong Shu Ting
     */
    public void resetGame() {
        game.reset();
        selectedPiece = null;
        moveHistory.clear();
        secondsElapsed = 0;
    }

    /**
     * Saves the game state to a text file.
     *
     * @param filename The filename to save the game state.
     * @author Tiffany Jong Shu Ting
     */
    public void saveGameAsText(String filename) {
        GameState gameState = new GameState(game.getBoard(), game.getCurrentPlayer(), game.getTurnCounter(), new ArrayList<>(moveHistory));
        gameState.setSecondsElapsed(this.secondsElapsed); // Save timer state
        try {
            GameSaver.saveGameAsText(gameState, filename);
            System.out.println("Game saved as text successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save the game as text.");
        }
    }

    /**
     * Loads the game state from a text file.
     *
     * @param filename The filename to load the game state from.
     * @author Joyce Ong Pay Teng
     */
    public void loadGame(String filename) {
        try {
            GameState gameState = GameLoader.loadGameFromTextFile(filename);
            game.setBoard(gameState.getBoard());
            game.setCurrentPlayer(gameState.getCurrentPlayer());
            game.setTurnCounter(gameState.getTurn());
            moveHistory.clear();
            moveHistory.addAll(gameState.getMoveHistory());
            this.secondsElapsed = gameState.getSecondsElapsed();
            selectedPiece = null;
            System.out.println("Game loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }
    }
}
