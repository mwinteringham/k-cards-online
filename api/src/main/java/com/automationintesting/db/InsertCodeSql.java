package com.automationintesting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCodeSql {

    private PreparedStatement preparedStatement;

    private String code;

    public InsertCodeSql(Connection connection, String code) throws SQLException {
        final String CREATE_ROOM = "INSERT INTO CODES (code) VALUES(?);";

        preparedStatement = connection.prepareStatement(CREATE_ROOM);
        preparedStatement.setString(1, code);
    }

    public PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }
}
