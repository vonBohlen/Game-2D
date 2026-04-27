package org.Game2D.engine.data.disk.conf;

import lombok.NonNull;
import org.Game2D.engine.data.runtime.Instance;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ConfManager {

    @NonNull
    public static String confPath = "./config.properties";

    protected static Properties conf = new Properties();

    public static void checkForFile() {
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

    public static void loadDefaultConf() {
        try {
            conf.load(Instance.class.getResourceAsStream("/conf/default.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeConf() {}

    public static void loadConf() {}

    public static void updateConf() {
        writeConf();
    }

    public static void addCustomConf(Properties conf) {
        updateConf();
    }
}
