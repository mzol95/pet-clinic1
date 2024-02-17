package pl.zoltowskimarcin.java.app.repository.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static ConnectionManager connectionManager = null;
    private static Connection connection = null;

    private static String path;
    private static String url;
    private static String user;
    private static String password;

    private ConnectionManager() {
    }




    public static Connection getInstance() {
        try {
            if (connectionManager == null || connection.isClosed()) {
                connectionManager = new ConnectionManager();
                loadProperties(path);
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void setPath(String path) {
        ConnectionManager.path = path;
    }

    private static void loadProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}