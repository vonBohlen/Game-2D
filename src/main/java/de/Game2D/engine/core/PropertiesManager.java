package de.Game2D.engine.core;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {

    public static Properties getSettings() {
        try {
            File file = new File("./settings.properties");
            if (!file.exists()) {file.createNewFile();
                OutputStream outputStream = new FileOutputStream("./settings.properties");
                Properties properties = new Properties();
                properties.setProperty("game2d.core.tps", "60");
                properties.setProperty("game2d.core.fps", "120");
                properties.setProperty("game2d.core.showDebugScreen", "false");
                properties.store(outputStream, "Game2D settings file");
            }
            InputStream input = new FileInputStream("./settings.properties");
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
