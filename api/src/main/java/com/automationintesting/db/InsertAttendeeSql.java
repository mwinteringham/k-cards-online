package com.automationintesting.db;

import com.automationintesting.model.Attendee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertAttendeeSql {

    private final PreparedStatement preparedStatement;

    public InsertAttendeeSql(Connection connection, Attendee attendee, String workshop) throws SQLException {
        final String CREATE_ATTENDEE = "INSERT INTO ATTENDEES (name, workshopcode) VALUES (?, ?)";

        preparedStatement = connection.prepareStatement(CREATE_ATTENDEE);
        preparedStatement.setString(1, attendee.getName());
        preparedStatement.setString(2, workshop);
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

}
