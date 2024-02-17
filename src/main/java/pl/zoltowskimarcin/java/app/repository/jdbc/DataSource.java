package pl.zoltowskimarcin.java.app.repository.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pl.zoltowskimarcin.java.app.utils.PropertyManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private DataSource() {
    }

    static {
        config.setJdbcUrl(PropertyManager.getProperty("dataSource.jdbcUrl"));
        config.setUsername(PropertyManager.getProperty("dataSource.user"));
        config.setPassword(PropertyManager.getProperty("dataSource.password"));
        config.addDataSourceProperty(PropertyManager.getProperty("dataSource.cachePrepStmts"), "true");
        config.addDataSourceProperty(PropertyManager.getProperty("dataSource.prepStmtCacheSize"), "250");
        config.addDataSourceProperty(PropertyManager.getProperty("dataSource.prepStmtCacheSqlLimit"), "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
