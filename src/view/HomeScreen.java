package view;

import controller.GameController;
import model.Board;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        // Frame settings
        setTitle("Kwazam Chess");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window on the screen

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 45, 85)); // Darker navy background
        titlePanel.setPreferredSize(new Dimension(800, 150));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Kwazam Chess", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
        buttonPanel.setBackground(new Color(230, 230, 230)); // Light gray background

        // Create Buttons with modern gradient styling
        CustomButton newGameButton = createGradientButton("Create New Game");
        CustomButton resumeGameButton = createGradientButton("Resume Game");
        CustomButton loadGameButton = createGradientButton("Load Game");
        CustomButton instructionsButton = createGradientButton("Instructions");
        CustomButton exitButton = createGradientButton("Exit");

        // Add Buttons to Panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(resumeGameButton);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(instructionsButton);
        buttonPanel.add(exitButton);

        // Add Panels to Frame
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Button Actions
        newGameButton.addActionListener(e -> startNewGame());
        resumeGameButton.addActionListener(e -> resumeGame());
        loadGameButton.addActionListener(e -> loadGame());
        instructionsButton.addActionListener(e -> showInstructions());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    /**
     * Helper method to create gradient-styled buttons with hover effects.
     */
    private CustomButton createGradientButton(String text) {
        CustomButton button = new CustomButton(text);

        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setHovered(true);
                button.repaint(); // Trigger repaint for the glow effect
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setHovered(false);
                button.repaint(); // Remove the glow effect
            }
        });

        return button;
    }

    private void startNewGame() {
        // Logic to start a new game
        dispose();
        new GameScreen(new GameController(new Board()));
    }

    private void resumeGame() {
        // Placeholder for resume game logic
        JOptionPane.showMessageDialog(this, "Resume game is not implemented yet.");
    }

    private void loadGame() {
        // Placeholder for load game logic
        JOptionPane.showMessageDialog(this, "Load game is not implemented yet.");
    }

    private void showInstructions() {
        // Display instructions in a popup or new window
        JOptionPane.showMessageDialog(this,
                "Welcome to Kwazam Chess!\n\n"
                        + "1. Click on your piece to see valid moves highlighted.\n"
                        + "2. Click on a green square to move your piece.\n"
                        + "3. Capture the opponent's 'Sau' to win the game.\n"
                        + "4. Some pieces transform after specific moves.",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * CustomButton class for gradient buttons with hover effects.
     */
    private static class CustomButton extends JButton {
        private boolean isHovered = false;

        public CustomButton(String text) {
            super(text);
        }

        public void setHovered(boolean hovered) {
            this.isHovered = hovered;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            // Create a gradient background
            GradientPaint gradient = new GradientPaint(0, 0, new Color(77, 182, 172), 0, height, new Color(38, 116, 128));
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, width, height, 15, 15);

            // Draw the flashlight effect
            if (isHovered) {
                g2d.setColor(new Color(255, 255, 255, 128)); // Semi-transparent white
                g2d.setStroke(new BasicStroke(4)); // Thicker border
                g2d.drawRoundRect(2, 2, width - 4, height - 4, 15, 15);
            }

            super.paintComponent(g);
        }
    }
}
