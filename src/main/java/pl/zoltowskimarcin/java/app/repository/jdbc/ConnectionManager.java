package pl.zoltowskimarcin.java.app.repository.jdbc;

import pl.zoltowskimarcin.java.app.utils.PropertyManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());


    private ConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = PropertyManager.getProperty("dataSource.jdbcUrl");
                String user = PropertyManager.getProperty("dataSource.user");
                String password = PropertyManager.getProperty("dataSource.password");
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to get connection", e);
        }
        return connection;
    }


}