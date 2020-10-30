package com.automationintesting.db;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class KCardDB {

    private Connection connection;

    private final String CREATE_DB = "CREATE table WORKSHOPS ( id int NOT NULL AUTO_INCREMENT, code varchar(6), workshopName varchar(255), primary key (id))";

    public KCardDB() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:kcard;MODE=MySQL");
        ds.setUser("user");
        ds.setPassword("password");
        connection = ds.getConnection();

        connection.prepareStatement(CREATE_DB).executeUpdate();
    }

    public Boolean addCode(String code, String workshopName) throws SQLException {
        InsertWorkshopSql insertWorkshopSql = new InsertWorkshopSql(connection, code, workshopName);
        PreparedStatement createPs = insertWorkshopSql.getPreparedStatement();

        return createPs.executeUpdate() > 0;
    }
}
