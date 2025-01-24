import view.HomeScreen;

/**
 * The Main class serves as the entry point for the Kwazam Chess application.
 *
 * Description:
 * This class contains the main method, which is the starting point of the Java application.
 * It initializes the graphical user interface by creating an instance of the HomeScreen class
 * on the Event Dispatch Thread (EDT) using SwingUtilities.invokeLater. This ensures that all
 * UI components are created and updated in a thread-safe manner.
 *
 * @author Tai Zhi Xuan
 */
public class Main {

    /**
     * The main method is the entry point of the application.
     *
     * Description:
     * This method schedules the creation and display of the HomeScreen GUI on the Event Dispatch Thread.
     * Using SwingUtilities.invokeLater ensures that the GUI is constructed and manipulated in a thread-safe way,
     * adhering to Swing's single-threaded rule.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        // Schedule the creation of the HomeScreen on the Event Dispatch Thread for thread safety
        javax.swing.SwingUtilities.invokeLater(HomeScreen::new);
    }
}

