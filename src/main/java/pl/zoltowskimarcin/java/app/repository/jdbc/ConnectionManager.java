package pl.zoltowskimarcin.java.app.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager connectionManager;
    private static Connection connection;

    private ConnectionManager(String url, String user, String password) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static ConnectionManager getInstance(String url, String user, String password) {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager(url, user, password);
        }
        return connectionManager;
    }


    public static Connection getConnection() {
        return connection;
    }
}
