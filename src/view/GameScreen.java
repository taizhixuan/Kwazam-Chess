// GameScreen.java
package view;

import controller.GameController;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreen extends BoardView {
    private final GameController controller;

    public GameScreen(GameController controller) {
        super(controller); // Call BoardView constructor
        this.controller = controller; // Store the controller instance
        addNavigationBar(); // Add navigation bar
    }

    private void addNavigationBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");

        // Set custom font size for the menu
        Font menuFont = new Font("Arial", Font.BOLD, 20);
        gameMenu.setFont(menuFont);

        // 1) New Game
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            controller.resetGame();
            super.refreshBoard();
            JOptionPane.showMessageDialog(this, "New game started!");
        });
        gameMenu.add(newGame);

        // 2) Save Game
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save your game");
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    controller.saveGameAsText(filename);
                    JOptionPane.showMessageDialog(this, "Game saved successfully to: " + filename);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Failed to save the game: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gameMenu.add(saveGame);

        // 3) Load Game
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
                    JOptionPane.showMessageDialog(this,
                            "Failed to load the game: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gameMenu.add(loadGame);

        // 4) Back to Home
        JMenuItem backHome = new JMenuItem("Back to Home");
        backHome.addActionListener(e -> {
            // Dispose this window and return to HomeScreen
            dispose();
            new HomeScreen();
        });
        gameMenu.add(backHome);

        // 5) Exit Game
        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> System.exit(0));
        gameMenu.add(exitGame);

        // Add the menu to the menu bar
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void refreshBoard() {
        super.refreshBoard(); // same logic as BoardView
    }

    @Override
    public void highlightValidMoves(List<Position> validMoves) {
        super.highlightValidMoves(validMoves);
    }

    @Override
    public void clearHighlights() {
        super.clearHighlights();
    }

    @Override
    public void gameOver(String message) {
        super.gameOver(message);
    }
}
