package pl.zoltowskimarcin.java.app.repository.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class ConnectionManagerTest {

    public static final String TEST_RESOURCES_DATABASE_PROPERTIES_PATH = "src/test/resources/database.properties";
    private Connection connection;

    @AfterEach
    public void closeConnection() {
        try {
            if (!ConnectionManager.getInstance().isClosed())
                ConnectionManager.getInstance().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void connection_is_not_null() {
        //given
        ConnectionManager.setPath(TEST_RESOURCES_DATABASE_PROPERTIES_PATH);
        ConnectionManager.getInstance();

        //when
        connection = ConnectionManager.getInstance();

        //then
        Assertions.assertNotNull(connection);

    }

    @Test
    void connection_is_closed() {
        //given
        ConnectionManager.setPath(TEST_RESOURCES_DATABASE_PROPERTIES_PATH);
        ConnectionManager.getInstance();
        boolean isClosed;

        //when
        connection = ConnectionManager.getInstance();
        try {
            ConnectionManager.getInstance().close();
            isClosed = connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //then
        Assertions.assertEquals(true, isClosed);
    }
}