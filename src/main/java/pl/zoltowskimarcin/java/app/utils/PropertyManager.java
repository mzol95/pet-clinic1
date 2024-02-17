package pl.zoltowskimarcin.java.app.utils;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager {

    private static final Logger LOGGER = Logger.getLogger(PropertyManager.class.getName());
    private static final String PROPERTY_FILE_NAME = "jdbc.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream inputStream =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(
                            PROPERTY_FILE_NAME);
            properties.load(inputStream);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Loading properties failed", e);
        }
    }

    private PropertyManager() {
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        return value;
    }

}
