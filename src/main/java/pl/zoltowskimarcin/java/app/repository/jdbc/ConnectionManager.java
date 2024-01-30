package pl.zoltowskimarcin.java.app.repository.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static ConnectionManager connectionManager;
    private static Connection connection;

    private String url;
    private String user;
    private String password;

    private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final String appConfigPath = rootPath + "java.properties";

    private ConnectionManager() {

        loadProperties();

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(appConfigPath));
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public static Connection getConnection() {
        return connection;
    }
}
