// BoardView.java
package view;

import controller.GameController;
import model.Board;
import model.Piece;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

/**
 * BoardView is the base graphical user interface (GUI) component for the Kwazam Chess game.
 * It extends JFrame and implements the ViewInterface to handle the visual representation
 * of the game board and user interactions.
 *
 * Design Pattern:Adapter Pattern
 * Role: Adaptee - Provides the foundational board view which is adapted by GameScreen to include additional UI components.
 *
 * @author Tai ZHi Xuan, Tiffany Jong Shu Ting
 */
public class BoardView extends JFrame implements ViewInterface {
    /**
     * The controller that manages interactions between the view and the model.
     */
    protected GameController controller;

    /**
     * The panel that contains all the board buttons.
     */
    private JPanel boardPanel;

    /**
     * A 2D array of buttons representing each tile on the game board.
     */
    private JButton[][] buttons;

    /**
     * The main panel that holds all UI components.
     */
    protected JPanel mainPanel;

    /**
     * The size of each button on the board.
     */
    private static final int BUTTON_SIZE = 83;

    /**
     * Constructs a new BoardView, initializing the GUI components.
     */
    public BoardView() {
        setTitle("Kwazam Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeBoard();
        setSize(BUTTON_SIZE * 7 + 200, BUTTON_SIZE * 8 + 200); // Adjust window size to fit the board appropriately
        setMinimumSize(new Dimension(BUTTON_SIZE * 7 + 200, BUTTON_SIZE * 8 + 200)); // Ensure minimum window size to fit the board
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }

    /**
     * Sets the GameController for this view.
     *
     * @param controller The GameController instance managing the game logic.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Retrieves the main panel of the board view.
     *
     * @return The main JPanel containing all UI components.
     */
    protected JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Initializes the game board GUI components, including the grid of buttons
     * and the row and column labels.
     */
    private void initializeBoard() {
        // Create the main panel with GridBagLayout for flexible component placement
        this.mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(getBackground());

        // Create the board panel with GridLayout for buttons (8 rows x 5 columns)
        boardPanel = new JPanel(new GridLayout(8, 5));
        buttons = new JButton[8][5];

        // Define the preferred and minimum size of the board panel
        Dimension boardSize = new Dimension(BUTTON_SIZE * 5, BUTTON_SIZE * 8);
        boardPanel.setPreferredSize(boardSize);
        boardPanel.setMinimumSize(boardSize);

        // Create labels for row numbers (0 to 7)
        JPanel rowLabels = new JPanel(new GridLayout(8, 1));
        for (int i = 0; i < 8; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Liberation Sans", Font.BOLD, 20));
            rowLabels.add(label);
        }

        // Create labels for column numbers (0 to 4)
        JPanel columnLabels = new JPanel(new GridLayout(1, 5));
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Liberation Sans", Font.BOLD, 20));
            columnLabels.add(label);
        }

        // Use GridBagConstraints to position the labels and board correctly
        GridBagConstraints gbc = new GridBagConstraints();

        // Position row labels to the left of the board
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 8;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(rowLabels, gbc);

        // Position column labels below the board
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 10, 0);
        mainPanel.add(columnLabels, gbc);

        // Position the board in the center
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(boardPanel, gbc);

        add(mainPanel);
    }

    /**
     * Refreshes the board display by updating piece positions and icons.
     * This method should be called whenever the game state changes.
     */
    public void refreshBoard() {
        boardPanel.removeAll();
        Board board = controller.getBoard();

        int buttonSize = boardPanel.getPreferredSize().width / 5;

        boolean isCurrentPlayerRed = controller.getCurrentPlayer().equals("RED");

        // Iterate through each position on the board
        for (int row = isCurrentPlayerRed ? board.getRows() - 1 : 0;
             isCurrentPlayerRed ? row >= 0 : row < board.getRows();
             row += isCurrentPlayerRed ? -1 : 1) {
            for (int col = isCurrentPlayerRed ? board.getColumns() - 1 : 0;
                 isCurrentPlayerRed ? col >= 0 : col < board.getColumns();
                 col += isCurrentPlayerRed ? -1 : 1) {

                JButton button = new JButton();
                button.setPreferredSize(new Dimension(buttonSize, buttonSize));
                buttons[row][col] = button;

                Position position = new Position(row, col);
                Piece piece = board.getPieceAt(position);

                if (piece != null) {
                    ImageIcon icon = new ImageIcon(
                            Objects.requireNonNull(getClass().getClassLoader().getResource(piece.getImagePath()))
                    );
                    Image originalImage = icon.getImage();
                    Image displayedImage = isCurrentPlayerRed ? rotateImage(originalImage) : originalImage;
                    Image scaledImage = displayedImage.getScaledInstance(buttonSize - 15, buttonSize - 15, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));
                }

                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.LIGHT_GRAY);
                } else {
                    button.setBackground(Color.DARK_GRAY);
                }
                button.setOpaque(true);
                button.setBorderPainted(false);

                final int r = row, c = col;
                button.addActionListener(e -> handleClick(r, c));

                boardPanel.add(button);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Handles user clicks on the board tiles.
     *
     * @param row The row index of the clicked tile.
     * @param col The column index of the clicked tile.
     */
    private void handleClick(int row, int col) {
        Position position = new Position(row, col);
        controller.handleTileClick(position, this);
    }

    /**
     * Highlights the selected piece by changing its background color to yellow.
     *
     * @param position The position of the selected piece.
     */
    public void highlightSelectedPiece(Position position) {
        int row = position.getRow();
        int col = position.getColumn();

        if (buttons[row][col] != null) {
            buttons[row][col].setBackground(Color.YELLOW);  // Set yellow background
        }
    }

    /**
     * Highlights all valid move positions by changing their background color to green.
     *
     * @param validMoves A list of valid positions to highlight.
     */
    public void highlightValidMoves(List<Position> validMoves) {
        for (Position move : validMoves) {
            buttons[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
        }
    }

    /**
     * Clears all highlights on the board by resetting the background colors to the checkerboard pattern.
     */
    public void clearHighlights() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if ((row + col) % 2 == 0) {
                    buttons[row][col].setBackground(Color.LIGHT_GRAY);
                } else {
                    buttons[row][col].setBackground(Color.DARK_GRAY);
                }
            }
        }
    }

    /**
     * Rotates an image by 180 degrees.
     *
     * @param original The original Image to rotate.
     * @return The rotated Image.
     */
    private Image rotateImage(Image original) {
        int width = original.getWidth(null);
        int height = original.getHeight(null);

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        g2d.translate(width / 2, height / 2);
        g2d.rotate(Math.toRadians(180));
        g2d.translate(-width / 2, -height / 2);

        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    /**
     * Displays the game over message and prompts the user to start a new game or exit.
     *
     * @param message The game over message to display.
     */
    public void gameOver(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.PLAIN_MESSAGE);

        int response = JOptionPane.showConfirmDialog(
                this,
                "Would you like to start a new game?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            controller.resetGame();
            refreshBoard();
        } else {
            System.exit(0);
        }
    }
}
