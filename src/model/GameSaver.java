//            // GameSaver.java
//            package model;
//
//            import com.google.gson.Gson;
//            import java.io.FileWriter;
//            import java.io.IOException;
//
//            public class GameSaver {
//                public void saveGame(String filePath, GameState gameState)
//                {
//                    Gson gson = new Gson();
//                    try (FileWriter writer = new FileWriter(filePath))
//                    {
//                        gson.toJson(gameState, writer);
//                        System.out.println("Game saved to " + filePath);
//                    } catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//    }




package model;

import java.io.*;

public class GameSaver {
    public static void saveGame(Game game, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(game);
        }
    }
}