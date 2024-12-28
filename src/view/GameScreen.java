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
    private JMenuBar menuBar;

    public GameScreen(GameController controller) {
        super(controller); // Call BoardView constructor
        addNavigationBar(); // Add navigation bar
    }

    /**
     * Adds a navigation bar to the game screen.
     */
    private void addNavigationBar() {
        menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            // Logic for starting a new game

            System.out.println("New game started!");
        });

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(e -> System.exit(0));

        gameMenu.add(newGame);
        gameMenu.add(exitGame);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar); // Set the menu bar for this JFrame
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
}
