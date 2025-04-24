package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static final Logger logger = LoggerFactory.getLogger(HikariCPDataSource.class);

    static {
        Properties prop = new Properties();
        try {
            prop.load(HikariCPDataSource.class.getResourceAsStream("/application.properties"));
        } catch (Exception e) {
            logger.error("Error while loading application properties", e);
        }

        config.setJdbcUrl(prop.getProperty("jdbc.url"));
        config.setUsername(prop.getProperty("jdbc.username"));
        config.setPassword(prop.getProperty("jdbc.password"));
        ds = new HikariDataSource(config);
    }

    private HikariCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
