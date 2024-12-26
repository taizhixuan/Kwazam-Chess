// GameSaver.java
package model;

import java.io.*;

public class GameSaver {
    public static void saveGame(Game game, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(game);
        }
    }
}