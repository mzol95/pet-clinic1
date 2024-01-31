package pl.zoltowskimarcin.java.app.repository.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class ConnectionManagerTest {

    private Connection connection;

    @AfterEach
    public void closeConnection() {
        ConnectionManager.closeConnection();
    }

    @Test
    void connection_is_not_null() {
        //given
        ConnectionManager.setPath("src/test/resources/java.properties");
        ConnectionManager.getInstance();

        //when
        connection = ConnectionManager.getConnection();

        //then
        Assertions.assertNotNull(connection);

    }

    @Test
    void connection_is_closed() {
        //given
        ConnectionManager.setPath("src/test/resources/java.properties");
        ConnectionManager.getInstance();
        boolean isClosed;

        //when
        connection = ConnectionManager.getConnection();
        ConnectionManager.closeConnection();
        try {
            isClosed = connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //then
        Assertions.assertEquals(true, isClosed);
    }
}