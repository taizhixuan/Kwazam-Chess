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

    private static final int BUTTON_SIZE = 100; // Adjust this for larger pieces

    public BoardView(GameController controller) {
        this.controller = controller;
        setTitle("Kwazam Chess");
        setSize(BUTTON_SIZE * 5 + 60, BUTTON_SIZE * 8 + 60); // Adjust frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeBoard();
        setVisible(true);
    }

    private void initializeBoard() {
        boardPanel = new JPanel(new GridLayout(8, 5)); // 8 rows, 5 columns
        buttons = new JButton[8][5];
        add(boardPanel, BorderLayout.CENTER);

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
