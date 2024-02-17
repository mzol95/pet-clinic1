package pl.zoltowskimarcin.java.app.repository.datasource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

class ConnectionManagerTest {

    private static Connection connection;

    @AfterEach
    public void closeConnection() throws FailedQueryExecutionException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FailedQueryExecutionException();
        }
    }

    @Test
    void connection_is_not_null() {
        //given

        //when
        connection = ConnectionManager.getConnection();

        //then
        Assertions.assertNotNull(connection);
    }

    @Test
    void connection_is_closed() {
        //given
        boolean isClosed = false;

        //when
        connection = ConnectionManager.getConnection();

        try {
            ConnectionManager.getConnection().close();
            isClosed = connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //then
        Assertions.assertEquals(true, isClosed);

    }
}