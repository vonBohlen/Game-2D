/**
 * /engine/data/disk/conf/ConfProvider.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Christian von Bohlen, J. K.
 */

package org.Game2D.engine.data.disk.conf;

import org.Game2D.engine.data.runtime.DataHand;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public class ConfProvider {

    public static Properties getConf(Path confPath) {

        File file = new File(String.valueOf(confPath));

        if (!file.exists()) return null;

        InputStream input;

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

    public static String getConfValue(String key) {
        return Objects.requireNonNull(getConf(DataHand.confPath)).getProperty(key);
    }

    public static int getConfValueAsInt(String key) {
        return Integer.parseInt(getConfValue(key));
    }

    public static float getConfValueAsFloat(String key) {
        return Float.parseFloat(getConfValue(key));
    }

    public static  boolean getConfValueAsBool(String key) {
        return Boolean.parseBoolean(getConfValue(key));
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

            } else return;

        }

        OutputStream outputStream;

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
