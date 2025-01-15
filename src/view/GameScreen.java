// GameScreen.java
package view;

import controller.GameController;
import model.Move;
import model.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * GameScreen
 */
public class GameScreen extends BoardView {
    private final GameController controller;

    // Sidebar components
    private JPanel sidePanel;
    private JLabel timerLabel;
    private Timer gameTimer;

    // Move history components
    private DefaultListModel<String> moveListModel;
    private JList<String> moveList;

    public GameScreen(GameController controller) {
        // 1) Create the BoardView
        super(controller); // Call BoardView constructor (which sets up mainPanel)
        this.controller = controller;

        // 2) Add the menu bar
        addNavigationBar(); // The menu bar

        // 3) Build the sidebar (including moveListModel, moveList)
        setupLayoutWithSidebar();

        // 4) Start the timer with the current secondsElapsed from controller
        startGameTimer();

        // 5) Now that everything is ready, refresh the board
        //    which also calls updateMoveList() to show moves
        refreshBoard();
    }

    /**
     * Reconfigure the frame so mainPanel is in the center,
     * and we have an additional panel on the right side for game status, controls, etc.
     */
    private void setupLayoutWithSidebar() {
        // 1) Remove everything from the current frame (including mainPanel).
        getContentPane().removeAll();
        // 2) Set a BorderLayout
        getContentPane().setLayout(new BorderLayout());

        // 3) Add BoardView's mainPanel to the center
        JPanel boardViewPanel = getMainPanel();
        getContentPane().add(boardViewPanel, BorderLayout.CENTER);

        // 4) Create our new sidePanel with increased width
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(300, getHeight())); // Increased width: 300
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the side panel

        // Add a gap at the top
        sidePanel.add(Box.createVerticalStrut(20));

        // 5) Timer label
        timerLabel = new JLabel("Time: " + controller.getSecondsElapsed() + "s"); // Initialize with controller's time
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(timerLabel);

        // Add some spacing after the timer
        sidePanel.add(Box.createVerticalStrut(30));

        // >>> CREATE moveListModel and moveList <<<
        moveListModel = new DefaultListModel<>();
        moveList = new JList<>(moveListModel);
        moveList.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Increased font size: 16

        // Add a label for the move list
        JLabel moveListLabel = new JLabel("Move History", SwingConstants.CENTER);
        moveListLabel.setFont(new Font("Arial", Font.BOLD, 16));
        moveListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Scroll pane for the list with added padding and increased width
        JScrollPane scrollPane = new JScrollPane(moveList);
        scrollPane.setPreferredSize(new Dimension(270, 300)); // Increased width: 270
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(10, 10, 10, 10) // Add padding inside the scroll pane
        ));

        // Add a border around the move history for better separation
        moveList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add components to the sidePanel
        sidePanel.add(moveListLabel);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(scrollPane);

        // Add some spacing at the bottom
        sidePanel.add(Box.createVerticalGlue());

        getContentPane().add(sidePanel, BorderLayout.EAST);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Sets up a javax.swing.Timer that increments `secondsElapsed` every second.
     */
    private void startGameTimer() {
        // Initialize the timer label with current secondsElapsed
        timerLabel.setText("Time: " + controller.getSecondsElapsed() + "s");

        // Fire an event every 1000 ms = 1 second
        gameTimer = new Timer(1000, e -> {
            controller.incrementSecondsElapsed(); // Increment in controller
            timerLabel.setText("Time: " + controller.getSecondsElapsed() + "s");
        });
        gameTimer.start();
    }

    /**
     * (Optional) Stop the timer if the game is over or the window is closed.
     */
    private void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    /**
     * Add your navigation bar / menu items here
     */
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
            // Reset the timer
            controller.setSecondsElapsed(0);
            timerLabel.setText("Time: 0s");
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
                    // Update the timer label
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

        // 4) Back to Home
        JMenuItem backHome = new JMenuItem("Back to Home");
        backHome.addActionListener(e -> {
            // Dispose this window and return to HomeScreen
            dispose();
            // Stop the timer
            stopGameTimer();
            new HomeScreen();
        });
        gameMenu.add(backHome);

        // 5) Exit Game
        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> {
            stopGameTimer();
            System.exit(0);
        });
        gameMenu.add(exitGame);

        // Add the menu to the menu bar
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void refreshBoard() {
        super.refreshBoard();
        updateMoveList();
    }

    /**
     * Pulls the entire move history from the controller
     * and displays it in the moveListModel with correct display coordinates.
     */
    private void updateMoveList() {
        // Clear old contents first
        moveListModel.clear();

        // Retrieve the entire move history from the controller
        List<Move> history = controller.getMoveHistory();
        boolean isRedPerspective = controller.getCurrentPlayer().equals("BLUE"); // Since player just switched

        int moveNumber = 1; // For numbering moves

        for (Move move : history) {
            String player = move.getPlayer();
            String pieceType = move.getPieceType(); // Get piece type

            // Determine if the move should be displayed with inversion
            boolean isRedPlayer = player.equalsIgnoreCase("RED");
            boolean shouldInvert = isRedPlayer;

            // Calculate display coordinates based on player perspective
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

        // Optionally, auto-scroll to the last move
        if (!history.isEmpty()) {
            moveList.ensureIndexIsVisible(history.size() - 1);
        }
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
        // Stop the timer if game is over
        stopGameTimer();
        super.gameOver(message);
    }
}
