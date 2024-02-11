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
    private String url;
    private String user;
    private String password;

    private ConnectionManager() {
    }


    //todo 08.02.24 init bezposrednio i runtime exception
    public static Connection getInstance() {
        try {
            if (connectionManager == null || connection.isClosed()) {
                connectionManager = new ConnectionManager();
                connectionManager.init();
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws SQLException {
        loadProperties(path);
        connection = DriverManager.getConnection(url, user, password);
    }

    public static void setPath(String path) {
        ConnectionManager.path = path;
    }

    //todo zmienic wyjatek 08.02 i do osobnej klasy
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
