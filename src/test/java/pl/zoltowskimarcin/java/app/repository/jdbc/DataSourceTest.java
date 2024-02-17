package pl.zoltowskimarcin.java.app.repository.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

class DataSourceTest {

    private static Connection connection;


    @BeforeEach
    public void getConnectionWithDatabase() {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void closeConnectionWithDatabase() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getConnection() {
        //given

        //when

        //then
        Assertions.assertNotNull(connection, "Connection is null");

    }
}