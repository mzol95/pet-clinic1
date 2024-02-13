package pl.zoltowskimarcin.java.app.repository.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig("src/main/resources/jdbc.properties");
    private static HikariDataSource ds;

    //todo 08.20.2024 zwracac DataSource i dopiero getConnection static block lepszy
    static {
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DataSource(){}
}
