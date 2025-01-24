package view;

import controller.GameController;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * HomeScreen provides the main menu interface for the Kwazam Chess application.
 * It allows users to start a new game, resume a previous game, load a game from a file,
 * view instructions, or exit the application.
 *
 * @author Tai Zhi Xuan, Tiffany Jong Shu Ting, Joyce Ong Pay Teng
 */
public class HomeScreen extends JFrame {
    /**
     * Static reference to the last GameController to facilitate game resumption.
     */
    private static GameController savedController = null;

    /**
     * Constructs a new HomeScreen, initializing the main menu UI components.
     */
    public HomeScreen() {
        // Frame settings
        setTitle("Kwazam Chess");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window on the screen

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 45, 85));
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
        buttonPanel.setBackground(new Color(230, 230, 230));

        // Create Buttons
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
     *
     * @param text The text to display on the button.
     * @return A CustomButton instance with gradient and hover effects.
     */
    private CustomButton createGradientButton(String text) {
        CustomButton button = new CustomButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setHovered(true);
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setHovered(false);
                button.repaint();
            }
        });
        return button;
    }

    /**
     * Creates a new game by initializing a new GameController and displaying the game screen.
     */
    private void startNewGame() {
        dispose(); // Close the HomeScreen
        GameController controller = new GameController(new Board());
        savedController = controller; // Store in static field for resumption
        controller.getView().setVisible(true); // Open the game screen
    }

    /**
     * Resumes a previously created game if a saved controller exists.
     * Displays an error message if no saved game is available.
     */
    private void resumeGame() {
        if (savedController == null) {
            JOptionPane.showMessageDialog(this,
                    "No saved game to resume!",
                    "Resume Game",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
        savedController.getView().setVisible(true);
    }

    /**
     * Loads an existing game from a file into a new GameController, then displays the game screen.
     */
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a game file to load");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            GameController controller = new GameController(new Board());
            try {
                controller.loadGame(filename);
                dispose();
                savedController = controller;
                controller.getView().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Failed to load the game: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Displays the game instructions in a dialog window with images and descriptions.
     */
    private void showInstructions() {
        URL bizBlueURL = getClass().getResource("/resources/images/Biz_blue.png");
        URL bizRedURL  = getClass().getResource("/resources/images/Biz_red.png");
        URL ramBlueURL = getClass().getResource("/resources/images/Ram_blue.png");
        URL ramRedURL  = getClass().getResource("/resources/images/Ram_red.png");
        URL torBlueURL = getClass().getResource("/resources/images/Tor_blue.png");
        URL torRedURL  = getClass().getResource("/resources/images/Tor_red.png");
        URL xorBlueURL = getClass().getResource("/resources/images/Xor_blue.png");
        URL xorRedURL  = getClass().getResource("/resources/images/Xor_red.png");
        URL sauBlueURL = getClass().getResource("/resources/images/Sau_blue.png");
        URL sauRedURL  = getClass().getResource("/resources/images/Sau_red.png");

        String instructionsHTML = "<html>" +
                "<h1 style='text-align:center;'>Kwazam Chess Instructions</h1>" +
                "<p style='font-size:14px;'>Below are the game rules and piece images for quick reference.</p>" +

                "<h2>Board</h2>" +
                "<ul>" +
                "  <li>Size: 5x8 grid.</li>" +
                "  <li>Pieces are arranged in specific starting positions.</li>" +
                "</ul>" +

                "<h2>Pieces</h2>" +
                // --- Biz ---
                "<h3>Biz</h3>" +
                "<p>" +
                "  <img src='" + bizBlueURL + "' width='50' height='50'>&nbsp;" +
                "  <img src='" + bizRedURL  + "' width='50' height='50'><br/>" +
                "  \u2022 Moves in an L-shape (like a knight).<br/>" +
                "  \u2022 Can jump over other pieces." +
                "</p>" +
                // --- Ram ---
                "<h3>Ram</h3>" +
                "<p>" +
                "  <img src='" + ramBlueURL + "' width='50' height='50'>&nbsp;" +
                "  <img src='" + ramRedURL  + "' width='50' height='50'><br/>" +
                "  \u2022 Moves one step forward.<br/>" +
                "  \u2022 Reverses direction at board edge." +
                "</p>" +
                // --- Tor ---
                "<h3>Tor</h3>" +
                "<p>" +
                "  <img src='" + torBlueURL + "' width='50' height='50'>&nbsp;" +
                "  <img src='" + torRedURL  + "' width='50' height='50'><br/>" +
                "  \u2022 Moves orthogonally any number of cells.<br/>" +
                "  \u2022 Cannot jump over other pieces.<br/>" +
                "  \u2022 Transforms into an <b>Xor</b> after 2 turns." +
                "</p>" +
                // --- Xor ---
                "<h3>Xor</h3>" +
                "<p>" +
                "  <img src='" + xorBlueURL + "' width='50' height='50'>&nbsp;" +
                "  <img src='" + xorRedURL  + "' width='50' height='50'><br/>" +
                "  \u2022 Moves diagonally any number of cells.<br/>" +
                "  \u2022 Cannot jump over other pieces.<br/>" +
                "  \u2022 Transforms back into a <b>Tor</b> after 2 turns." +
                "</p>" +
                // --- Sau ---
                "<h3>Sau</h3>" +
                "<p>" +
                "  <img src='" + sauBlueURL + "' width='50' height='50'>&nbsp;" +
                "  <img src='" + sauRedURL  + "' width='50' height='50'><br/>" +
                "  \u2022 Moves one step in any direction.<br/>" +
                "  \u2022 Key piece: capturing a Sau ends the game." +
                "</p>" +

                "<h2>Turns</h2>" +
                "<ul>" +
                "  <li>Players alternate turns.</li>" +
                "  <li>After each Red & Blue move (2 total), Tor & Xor transform.</li>" +
                "</ul>" +

                "<h2>Saving & Loading</h2>" +
                "<ul>" +
                "  <li>Save the game at any point to a text file.</li>" +
                "  <li>Load a saved state to resume the same board.</li>" +
                "</ul>" +

                "<h2>Basic GUI Steps</h2>" +
                "<ol>" +
                "  <li>Click on your piece to see valid moves highlighted in green.</li>" +
                "  <li>Click a highlighted square to move there.</li>" +
                "  <li>Capture the opponent's Sau to win!</li>" +
                "</ol>" +

                "</html>";

        JLabel instructionsLabel = new JLabel(instructionsHTML);
        JScrollPane scrollPane = new JScrollPane(instructionsLabel);
        scrollPane.setPreferredSize(new Dimension(550, 500));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Kwazam Chess Instructions",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * CustomButton class extends JButton to provide gradient backgrounds and hover effects.
     */
    private static class CustomButton extends JButton {
        /**
         * Flag indicating whether the mouse is hovering over the button.
         */
        private boolean isHovered = false;

        /**
         * Constructs a new CustomButton with the specified text.
         *
         * @param text The text to display on the button.
         */
        public CustomButton(String text) {
            super(text);
        }

        /**
         * Sets the hovered state of the button.
         *
         * @param hovered True if the mouse is hovering over the button, false otherwise.
         */
        public void setHovered(boolean hovered) {
            this.isHovered = hovered;
        }

        /**
         * Paints the component with gradient background and hover effects.
         *
         * @param g The Graphics object used for drawing.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(77, 182, 172),
                    0, height, new Color(38, 116, 128)
            );
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, width, height, 15, 15);

            if (isHovered) {
                g2d.setColor(new Color(255, 255, 255, 128));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRoundRect(2, 2, width - 4, height - 4, 15, 15);
            }
            super.paintComponent(g);
        }
    }
}
