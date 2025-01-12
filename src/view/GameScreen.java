// GameScreen.java
package view;

import controller.GameController;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreen extends BoardView {
    private final GameController controller;

    // Sidebar components
    private JPanel sidePanel;
    private JLabel currentPlayerLabel;
    // You can add a timer label or other controls here
    private JLabel timerLabel;

    public GameScreen(GameController controller) {
        super(controller); // Call BoardView constructor (which sets up mainPanel)
        this.controller = controller;
        addNavigationBar(); // The menu bar you already implemented

        // Now rearrange layout to add a sidebar:
        setupLayoutWithSidebar();
    }

    /**
     * Reconfigure the frame so mainPanel is in the center, and we have
     * an additional panel on the right side for game status, controls, etc.
     */
    private void setupLayoutWithSidebar() {
        // 1) Remove everything from the current frame (including mainPanel).
        getContentPane().removeAll();
        // 2) Set a BorderLayout
        getContentPane().setLayout(new BorderLayout());

        // 3) Add BoardView's mainPanel to the center
        //    (We exposed it via getMainPanel().)
        JPanel boardViewPanel = getMainPanel();
        getContentPane().add(boardViewPanel, BorderLayout.CENTER);

        // 4) Create our new sidePanel
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        // Add a gap at the top
        sidePanel.add(Box.createVerticalStrut(20));

        // A label for "Current Player"
        currentPlayerLabel = new JLabel("Current Player: " + controller.getCurrentPlayer());
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(currentPlayerLabel);

        sidePanel.add(Box.createVerticalStrut(20));

        // A placeholder "Timer" label
        timerLabel = new JLabel("Timer: [Not Implemented]");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(timerLabel);

        // You could add more controls (buttons, text fields, etc.) here...

        // 5) Add sidePanel to the east
        getContentPane().add(sidePanel, BorderLayout.EAST);

        // 6) Revalidate & repaint
        getContentPane().revalidate();
        getContentPane().repaint();
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
