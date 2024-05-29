package de.Game2D.engine.utils;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class ConfProvider {

    public  static Properties getConf(Path confPath) {

        File file = new File(String.valueOf(confPath));

        if (!file.exists()) return null;

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

    public static void writeConf(Properties properties, Path confPath, String tMod, boolean generate) {

        File file = new File(String.valueOf(confPath));

        boolean exists = file.exists();

        if (!exists) {

            if (generate) {

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            else return;

        }

        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(String.valueOf(confPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            properties.store(outputStream, tMod);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
