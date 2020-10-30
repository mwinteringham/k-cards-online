package com.automationintesting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertWorkshopSql {

    private final PreparedStatement preparedStatement;

    public InsertWorkshopSql(Connection connection, String code, String workshopName) throws SQLException {
        final String CREATE_WORKSHOP = "INSERT INTO WORKSHOPS (code, workshopName) VALUES(?, ?);";

        preparedStatement = connection.prepareStatement(CREATE_WORKSHOP);
        preparedStatement.setString(1, code);
        preparedStatement.setString(2, workshopName);
    }

    public PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }
}
