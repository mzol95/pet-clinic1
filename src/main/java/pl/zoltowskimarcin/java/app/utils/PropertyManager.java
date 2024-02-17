package pl.zoltowskimarcin.java.app.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager {
    private static final Logger LOGGER = Logger.getLogger(PropertyManager.class.getName());

    private static final String PROPERTY_FILE_NAME = "petclinic.properties";

    private static PropertyManager propertyManager;
    private static Properties properties;

    private PropertyManager() {
        properties = new Properties();

        try {
            InputStream inputStream =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(
                            PROPERTY_FILE_NAME);
            properties.load(inputStream);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong", e);
            //throw new P //todo
        }
    }

    public static PropertyManager getInstance() {
        if (propertyManager == null) {
            propertyManager = new PropertyManager();
        }

        return propertyManager;
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        return value;
    }

}
