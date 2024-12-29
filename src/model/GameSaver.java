package model;

import java.io.*;

public class GameSaver {
    // Save the game state to a file using ObjectOutputStream
    public static void saveGame(GameState gameState, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(gameState);  // Serialize the game state to the file
        }
    }

    // Load the game state from a file using ObjectInputStream
    public static GameState loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) ois.readObject();  // Deserialize and return the game state
        }
    }
}
