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
            e.printStackTrace();
        }
    }

    @Test
    void connection_is_not_null() {
        //given
        ConnectionManager.setPath("src/test/resources/database.properties");
        //when

        connection = ConnectionManager.getInstance();

        //then
        Assertions.assertNotNull(connection);
    }

    @Test
    void connection_is_closed() {
        //given
        boolean isClosed = false;

        //when
        connection = ConnectionManager.getInstance();
        try {
            ConnectionManager.getInstance().close();
            isClosed = connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //then
        Assertions.assertEquals(true, isClosed);

    }
}