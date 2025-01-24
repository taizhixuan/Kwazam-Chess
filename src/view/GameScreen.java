package view;

import controller.GameController;
import model.Move;
import model.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GameScreen extends BoardView to include additional user interface components
 * such as move history and a game timer. It enhances the base board view with
 * more interactive and informative elements.
 *
 * Design Pattern: Adapter Pattern
 * Role: Adapter - Extends BoardView to add new functionalities like sidebar and timer.
 *
 * @author Tai Zhi Xuan
 */
public class GameScreen extends BoardView {
    /**
     * The controller managing the game logic and interactions.
     */
    private final GameController controller;

    /**
     * Panel for additional UI components like move history and timer.
     */
    private JPanel sidePanel;

    /**
     * Label displaying the elapsed game time.
     */
    private JLabel timerLabel;

    /**
     * Timer object to update the game timer every second.
     */
    private Timer gameTimer;

    /**
     * Model for the move history list.
     */
    private DefaultListModel<String> moveListModel;

    /**
     * List component displaying the move history.
     */
    private JList<String> moveList;

    /**
     * Constructs a new GameScreen with the specified GameController.
     *
     * @param controller The GameController instance managing the game.
     */
    public GameScreen(GameController controller) {
        super(); // Call BoardView constructor
        this.controller = controller;
        setController(controller); // Set the controller in BoardView

        // Add the menu bar with navigation options
        addNavigationBar();

        // Build the sidebar containing move history and timer
        setupLayoutWithSidebar();

        // Start the game timer with the current elapsed time
        startGameTimer();

        // Refresh the board to display the initial state
        refreshBoard();

        // Add a WindowListener to handle window closing events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopGameTimer();
                super.windowClosing(e);
            }
        });
    }

    /**
     * Overrides the setVisible method to restart the timer when the window is shown.
     *
     * @param visible True to make the window visible, false to hide it.
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && gameTimer != null && !gameTimer.isRunning()) {
            gameTimer.start();
        }
    }

    /**
     * Configures the layout to include a sidebar for move history and timer.
     */
    private void setupLayoutWithSidebar() {
        // Remove all existing components from the frame
        getContentPane().removeAll();
        // Set the layout to BorderLayout for main and sidebar panels
        getContentPane().setLayout(new BorderLayout());

        // Add the existing board view to the center
        JPanel boardViewPanel = getMainPanel();
        getContentPane().add(boardViewPanel, BorderLayout.CENTER);

        // Create a new sidePanel with a fixed width
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(300, getHeight()));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add vertical spacing at the top
        sidePanel.add(Box.createVerticalStrut(20));

        // Initialize the timer label with the current elapsed time
        timerLabel = new JLabel("Time: " + controller.getSecondsElapsed() + "s");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(timerLabel);

        // Add vertical spacing after the timer
        sidePanel.add(Box.createVerticalStrut(30));

        // Initialize the move history list model and list
        moveListModel = new DefaultListModel<>();
        moveList = new JList<>(moveListModel);
        moveList.setFont(new Font("Monospaced", Font.PLAIN, 16));

        // Add a label for the move history section
        JLabel moveListLabel = new JLabel("Move History", SwingConstants.CENTER);
        moveListLabel.setFont(new Font("Arial", Font.BOLD, 16));
        moveListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a scroll pane for the move history list with padding and borders
        JScrollPane scrollPane = new JScrollPane(moveList);
        scrollPane.setPreferredSize(new Dimension(270, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Add a border around the move history for better separation
        moveList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add components to the sidePanel
        sidePanel.add(moveListLabel);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(scrollPane);

        // Add glue to push components to the top
        sidePanel.add(Box.createVerticalGlue());

        // Add the sidePanel to the right side of the frame
        getContentPane().add(sidePanel, BorderLayout.EAST);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Sets up a javax.swing.Timer that increments `secondsElapsed` every second.
     * This timer updates the timer label to show the elapsed game time.
     */
    private void startGameTimer() {
        // Initialize the timer label with current secondsElapsed
        timerLabel.setText("Time: " + controller.getSecondsElapsed() + "s");

        // Create a timer that fires every 1000 ms (1 second)
        gameTimer = new Timer(1000, e -> {
            controller.incrementSecondsElapsed(); // Increment in controller
            timerLabel.setText("Time: " + controller.getSecondsElapsed() + "s"); // Update timer label
        });
        gameTimer.start(); // Start the timer
    }

    /**
     * Stops the game timer to prevent it from running in the background.
     */
    private void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    /**
     * Adds the navigation menu bar with options like New Game, Save Game, Load Game, etc.
     */
    private void addNavigationBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");

        Font menuFont = new Font("Arial", Font.BOLD, 16);
        gameMenu.setFont(menuFont);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            controller.resetGame();
            super.refreshBoard();
            controller.setSecondsElapsed(0);
            timerLabel.setText("Time: 0s");
            JOptionPane.showMessageDialog(this, "New game started!");
        });
        gameMenu.add(newGame);

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
                    timerLabel.setText("Time: " + controller.getSecondsElapsed() + "s");
                    JOptionPane.showMessageDialog(this, "Game loaded successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Failed to load the game: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gameMenu.add(loadGame);

        JMenuItem backHome = new JMenuItem("Back to Home");
        backHome.addActionListener(e -> {
            dispose();
            stopGameTimer();
            new HomeScreen();
        });
        gameMenu.add(backHome);

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> {
            stopGameTimer();
            System.exit(0);
        });
        gameMenu.add(exitGame);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Refreshes the board and updates the move history list.
     */
    @Override
    public void refreshBoard() {
        super.refreshBoard();
        updateMoveList();
    }

    /**
     * Updates the move history display based on the controller's move history.
     * This method formats each move and adds it to the moveListModel.
     */
    public void updateMoveList() {
        moveListModel.clear();

        List<Move> history = controller.getMoveHistory();
        boolean isRedPerspective = controller.getCurrentPlayer().equals("BLUE");

        int moveNumber = 1;

        for (Move move : history) {
            String player = move.getPlayer();
            String pieceType = move.getPieceType();

            boolean isRedPlayer = player.equalsIgnoreCase("RED");
            boolean shouldInvert = isRedPlayer;

            int displayFromRow = shouldInvert ? (7 - move.getFrom().getRow()) : move.getFrom().getRow();
            int displayFromCol = shouldInvert ? (4 - move.getFrom().getColumn()) : move.getFrom().getColumn();
            int displayToRow = shouldInvert ? (7 - move.getTo().getRow()) : move.getTo().getRow();
            int displayToCol = shouldInvert ? (4 - move.getTo().getColumn()) : move.getTo().getColumn();

            String formattedMove = String.format(
                    "%d. %s (%s): (%d,%d) -> (%d,%d)",
                    moveNumber++,
                    player,
                    pieceType,
                    displayFromRow, displayFromCol,
                    displayToRow, displayToCol
            );

            moveListModel.addElement(formattedMove);
        }

        if (!history.isEmpty()) {
            moveList.ensureIndexIsVisible(history.size() - 1);
        }
    }

    /**
     * Highlights valid move positions in green.
     *
     * @param validMoves List of valid positions to highlight.
     */
    @Override
    public void highlightValidMoves(List<Position> validMoves) {
        super.highlightValidMoves(validMoves);
    }

    /**
     * Clears all highlights on the board.
     */
    @Override
    public void clearHighlights() {
        super.clearHighlights();
    }

    /**
     * Handles game over scenarios by stopping the timer and displaying messages.
     *
     * @param message The game over message to display.
     */
    @Override
    public void gameOver(String message) {
        stopGameTimer();
        super.gameOver(message);
    }
}
