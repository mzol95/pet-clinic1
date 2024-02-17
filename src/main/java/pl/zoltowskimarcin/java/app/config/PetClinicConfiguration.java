package pl.zoltowskimarcin.java.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.zoltowskimarcin.java.app.utils.PropertyManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class PetClinicConfiguration {
    //@Bean
    public Connection getJdbcConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(PropertyManager.getProperty("url"),
                    PropertyManager.getProperty("user"),
                    PropertyManager.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
