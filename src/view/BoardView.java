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
 * Swing-based GUI for Kwazam Chess.
 */
public class BoardView extends JFrame {
    private final GameController controller;
    private JPanel boardPanel;
    private JButton[][] buttons;

    // Make the mainPanel protected so subclasses can rearrange it
    protected JPanel mainPanel;

    private static final int BUTTON_SIZE = 90; // Adjust this for larger pieces

    public BoardView(GameController controller) {
        this.controller = controller;
        setTitle("Kwazam Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeBoard();

        setSize(BUTTON_SIZE * 7 + 100, BUTTON_SIZE * 9 + 100); // Adjust window size to fit the board appropriately // Adjust window size to fit the even smaller board // Adjust window size to fit the smaller board // Adjust window size to fit margins and labels // Adjust window size to fit board and labels // Set initial window size
        setMinimumSize(new Dimension(BUTTON_SIZE * 7 + 100, BUTTON_SIZE * 9 + 100)); // Ensure minimum window size to fit the board // Ensure minimum window size for the smaller board // Ensure minimum window size for smaller board // Ensure fixed size including margins // Ensure minimum window size
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }

    /**
     * Provide access to the main panel, so that a subclass can
     * place it in a different layout or add a sidebar.
     */
    protected JPanel getMainPanel() {
        return mainPanel;
    }

    private void initializeBoard() {
        // Assign to the class field mainPanel, not a local variable
        this.mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(getBackground());

        // Create the board panel with GridLayout for buttons
        boardPanel = new JPanel(new GridLayout(8, 5));
        buttons = new JButton[8][5];
        boardPanel.setPreferredSize(new Dimension(BUTTON_SIZE * 5, BUTTON_SIZE * 8));

        // Create labels for row numbers
        JPanel rowLabels = new JPanel(new GridLayout(8, 1));
        for (int i = 0; i < 8; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Liberation Sans", Font.BOLD, 22));
            rowLabels.add(label);
        }

        // Create labels for column numbers
        JPanel columnLabels = new JPanel(new GridLayout(1, 5));
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Liberation Sans", Font.BOLD, 22));
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
        gbc.insets = new Insets(10, 0, 0, 0); // Add spacing between column labels and the board
        mainPanel.add(rowLabels, gbc);

        // Position column labels below the board
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 10, 0); // Add spacing between row labels and the board
        mainPanel.add(columnLabels, gbc);

        // Position the board in the center
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(boardPanel, gbc);

        // Finally, add mainPanel to the frame
        add(mainPanel);
        refreshBoard();
    }


    /**
     * Redraws the board with updated piece positions and icons.
     */
    public void refreshBoard() {
        boardPanel.removeAll();
        Board board = controller.getBoard();

        boolean isCurrentPlayerRed = controller.getCurrentPlayer().equals("RED");

        for (int row = isCurrentPlayerRed ? board.getRows() - 1 : 0;
             isCurrentPlayerRed ? row >= 0 : row < board.getRows();
             row += isCurrentPlayerRed ? -1 : 1) {
            for (int col = isCurrentPlayerRed ? board.getColumns() - 1 : 0;
                 isCurrentPlayerRed ? col >= 0 : col < board.getColumns();
                 col += isCurrentPlayerRed ? -1 : 1) {

                JButton button = new JButton();
                button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                buttons[row][col] = button;

                Position position = new Position(row, col);
                Piece piece = board.getPieceAt(position);

                // Set piece icon with 180-degree rotation if necessary
                if (piece != null) {
                    ImageIcon icon = new ImageIcon(
                            Objects.requireNonNull(getClass().getClassLoader().getResource(piece.getImagePath()))
                    );
                    Image originalImage = icon.getImage();
                    Image displayedImage = isCurrentPlayerRed ? rotateImage(originalImage) : originalImage;
                    Image scaledImage = displayedImage.getScaledInstance(BUTTON_SIZE - 15, BUTTON_SIZE - 15, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));
                }

                // Checkerboard background
                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.LIGHT_GRAY);
                } else {
                    button.setBackground(Color.DARK_GRAY);
                }
                button.setOpaque(true);
                button.setBorderPainted(false);

                // Add click listener
                final int r = row, c = col;
                button.addActionListener(e -> handleClick(r, c));

                boardPanel.add(button);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }


    /**
     * Handles user clicks on the board.
     */
    private void handleClick(int row, int col) {
        Position position = new Position(row, col);
        controller.handleTileClick(position, this);
    }


    /**
     * Highlights the selected piece in yellow.
     */
    public void highlightSelectedPiece(Position position) {
        int row = position.getRow();
        int col = position.getColumn();

        if (buttons[row][col] != null) {
            buttons[row][col].setBackground(Color.YELLOW);  // Set yellow background
        }
    }


    /**
     * Highlights valid move positions in green.
     */
    public void highlightValidMoves(List<Position> validMoves) {
        for (Position move : validMoves) {
            buttons[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
        }
    }


    /**
     * Clears all highlights and resets the checkerboard.
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
     * Rotate the image (pieces) 180 degree when the board is flip
     */
    private Image rotateImage(Image original) {
        int width = original.getWidth(null);
        int height = original.getHeight(null);

        // Create a new buffered image with the same dimensions
        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Translate to the center of the image, rotate, then translate back
        g2d.translate(width / 2, height / 2);
        g2d.rotate(Math.toRadians(180));
        g2d.translate(-width / 2, -height / 2);

        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }


    /**
     * Display the game over message by using dialog box.
     * Ask the user if they would like to start a new game or exit the application.
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
            controller.resetGame(); // Reset the game state
            refreshBoard();         // Refresh the game board
        } else {
            System.exit(0);          // Exit the game
        }
    }
}
