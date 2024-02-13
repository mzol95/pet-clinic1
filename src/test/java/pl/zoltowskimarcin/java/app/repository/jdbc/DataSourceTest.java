package pl.zoltowskimarcin.java.app.repository.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

class DataSourceTest {

    public static final String TEST_RESOURCES_DATABASE_PROPERTIES_PATH = "src/test/resources/jdbc.properties";
    private static Connection connection;


    @BeforeEach
    public void getConnectionWithDatabase() {
        try {
            HikariConfig config = new HikariConfig(TEST_RESOURCES_DATABASE_PROPERTIES_PATH);
            HikariDataSource ds = new HikariDataSource(config);
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        //when

        //then
        Assertions.assertNotNull(connection, "Connection is null");

    }
}