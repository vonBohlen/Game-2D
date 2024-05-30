package de.Game2D.engine_old.core;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {

    public static Properties getSettings() {
        try {
            File file = new File("./config.properties");
            if (!file.exists()) {file.createNewFile();
                OutputStream outputStream = new FileOutputStream("./config.properties");
                Properties properties = new Properties();
                properties.setProperty("game2d.core.tps", "60");
                properties.setProperty("game2d.core.fps", "120");
                properties.setProperty("game2d.core.showDebugScreen", "false");
                properties.store(outputStream, "Game2D settings file");
            }
            InputStream input = new FileInputStream("./config.properties");
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
