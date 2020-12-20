package com.automationintesting.db;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KCardConnectionBuilder {

    private final Connection connection;

    public KCardConnectionBuilder() throws SQLException {
        Logger logger = LoggerFactory.getLogger(KCardConnectionBuilder.class);

        String MYSQL_HOST = System.getenv("MYSQL_HOST");
        String MYSQL_USER = System.getenv("MYSQL_USER");
        String MYSQL_PASS = System.getenv("MYSQL_PASS");

        if(MYSQL_HOST != null && MYSQL_USER != null && MYSQL_PASS != null){
            logger.info("Found DB credentials, connecting to production DB");
            connection = DriverManager.getConnection(MYSQL_HOST, MYSQL_USER, MYSQL_PASS);
        } else {
            logger.info("Incomplete DB credentials set, falling back to dev DB");
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL("jdbc:h2:mem:kcard;MODE=MySQL");
            ds.setUser("user");
            ds.setPassword("password");
            connection = ds.getConnection();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
