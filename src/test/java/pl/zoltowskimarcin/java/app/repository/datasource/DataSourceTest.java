package pl.zoltowskimarcin.java.app.repository.datasource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.repository.jdbc.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
@Tag("plain")
class DataSourceTest {

    private static Connection connection;


    @AfterAll
    public static void closeConnectionWithDatabase() throws FailedQueryExecutionException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FailedQueryExecutionException();
        }
    }

    @Test
    void getConnection() throws FailedQueryExecutionException {
        //given

        //when
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FailedQueryExecutionException();
        }

        //then
        Assertions.assertNotNull(connection, "Connection is null");

    }
}