package com.automationintesting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCodeSql {

    private final PreparedStatement preparedStatement;

    public InsertCodeSql(Connection connection, String code, String workshopName) throws SQLException {
        final String CREATE_ROOM = "INSERT INTO CODES (code, workshopName) VALUES(?, ?);";

        preparedStatement = connection.prepareStatement(CREATE_ROOM);
        preparedStatement.setString(1, code);
        preparedStatement.setString(2, workshopName);
    }

    public PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }
}
