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
    private static String path;
    private String url;
    private String user;
    private String password;

    public static void setPath(String path) {
        ConnectionManager.path = path;
    }

    private ConnectionManager() {

        loadProperties(path);

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connectionManager = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void loadProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
