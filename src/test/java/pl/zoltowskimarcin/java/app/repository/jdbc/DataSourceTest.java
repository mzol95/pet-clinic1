package pl.zoltowskimarcin.java.app.repository.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class DataSourceTest {

    public static final String TEST_RESOURCES_DATABASE_PROPERTIES_PATH = "src/test/resources/database.properties";
    private static Connection connection;


    @BeforeAll
    public static void getConnectionWithDatabase() {
        try {
            DataSource.setPath(TEST_RESOURCES_DATABASE_PROPERTIES_PATH);
            DataSource.getInstance();
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DataSource.setPath(TEST_RESOURCES_DATABASE_PROPERTIES_PATH);

    }

    @AfterEach
    public void closeConnectionWithDatabase() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getConnection() {
        //given
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //when

        //then
        Assertions.assertNotNull(connection, "Connection is null");

    }
}