package de.Game2D.engine.utils;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class ConfProvider {

    public  static Properties getConf(Path confPath, boolean generate) {

        File file = new File(String.valueOf(confPath));

        boolean exists = file.exists();

        if (generate && !exists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        InputStream input = null;
        try {
            input = new FileInputStream(String.valueOf(confPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();

        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;

    }
}
