// GameLoader.java
package model;

import java.io.*;

public class GameLoader {
    public static Game loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Game) ois.readObject();
        }
    }
}
