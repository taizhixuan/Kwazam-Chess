// GameScreen.java
package view;

import controller.GameController;
import model.Board;
import model.Piece;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GameScreen that extends BoardView for consistency and adds a navigation bar.
 */
public class GameScreen extends BoardView {
    private final GameController controller;

    public GameScreen(GameController controller) {
        super(controller); // Call BoardView constructor
        this.controller = controller; // Store the controller instance
        addNavigationBar(); // Add navigation bar
    }

    /**
     * Adds a navigation bar to the game screen.
     */
    private void addNavigationBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            // Logic for starting a new game
            // Reset the game through the controller
            controller.resetGame();

            // Refresh the board display
            super.refreshBoard();

            JOptionPane.showMessageDialog(this, "New game started!");
        });

        JMenuItem saveGame = getJMenuItem();

        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a game file to load");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    controller.loadGame(filename);
                    refreshBoard();
                    JOptionPane.showMessageDialog(this, "Game loaded successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Failed to load the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> System.exit(0));

        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        gameMenu.add(loadGame);
        gameMenu.add(exitGame);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar); // Set the menu bar for this JFrame
    }

    private JMenuItem getJMenuItem() {
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save your game");
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    controller.saveGame(filename);
                    JOptionPane.showMessageDialog(this, "Game saved successfully to: " + filename);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save the game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return saveGame;
    }

    @Override
    public void refreshBoard() {
        super.refreshBoard(); // Use the same refresh logic as in BoardView
    }

    @Override
    public void highlightValidMoves(List<Position> validMoves) {
        super.highlightValidMoves(validMoves); // Use highlight logic from BoardView
    }

    @Override
    public void clearHighlights() {
        super.clearHighlights(); // Use clear highlight logic from BoardView
    }

    @Override
    public void gameOver(String message) {
        super.gameOver(message);
    }
}
