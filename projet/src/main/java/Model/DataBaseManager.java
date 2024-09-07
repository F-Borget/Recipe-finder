package Model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataBaseManager {
    private static final String JDBC_URL = "jdbc:sqlite:src/main/resources/fridgeDB.db";
    private static final DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setMinimumIdle(20);
        config.setMaximumPoolSize(500);
        config.setIdleTimeout(60000);
        config.setValidationTimeout(3000);
        config.setMaxLifetime(2000000);
        config.setConnectionTimeout(600000);

        dataSource = new HikariDataSource(config);
    }
    public static DataSource getDataSource() {
        return dataSource;
    }
}
