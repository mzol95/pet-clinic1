package pl.zoltowskimarcin.java.app.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyManager {

    private PropertyManager() {
    }

    public static Properties getProperties(String propertyPath) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(propertyPath)) {
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }


}
