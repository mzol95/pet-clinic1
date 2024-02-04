package pl.zoltowskimarcin.java.app.repository.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static String path;

    private DataSource() {
    }

    public static void getInstance(){
        Properties properties = new Properties();

        try(FileInputStream input = new FileInputStream(path)){
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("user"));
        config.setPassword(properties.getProperty("password"));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void setPath(String path) {
        DataSource.path = path;
    }


}