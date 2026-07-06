package org.Game2D.engine.data.disk.conf;

import lombok.NonNull;
import org.Game2D.engine.data.runtime.Instance;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ConfManager {

    @NonNull
    public static String confPath = "./config.properties";

    private static final Properties conf = new Properties();

    private static void checkForFile() {
        File file = new File(confPath);
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loadDefaultConf();
            writeConf();
        }
    }

    public static void writeConf() {
        checkForFile();
        try {
            OutputStream outputStream = new FileOutputStream(confPath);
            conf.store(outputStream, "Game2D configuration file");
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadDefaultConf() {
        Properties defaultConf = new Properties();
        try {
            defaultConf.load(ConfManager.class.getResourceAsStream("/conf/default.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry entry : defaultConf.entrySet()) {
            if (!conf.containsKey(entry.getKey())) conf.put(entry.getKey(), entry.getValue());
        }
    }

    public static void loadConf() {
        checkForFile();
        try {
            InputStream inputStream = new FileInputStream(confPath);
            conf.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCustomConf(Properties conf) {
        ConfManager.conf.putAll(conf);
    }

    public static String getConfValue(String key) {
        return Objects.requireNonNull(conf.getProperty(key));
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

}
