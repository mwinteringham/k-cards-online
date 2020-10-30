package com.automationintesting.db;

import com.automationintesting.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertActivitySql {

    private final PreparedStatement preparedStatement;

    public InsertActivitySql(Connection connection, Card card, String workshopCode) throws SQLException {
        final String CREATE_ACTIVITY = "INSERT INTO ACTIVITIES (cardtype, name, workshopcode) VALUES (?, ?, ?)";

        preparedStatement = connection.prepareStatement(CREATE_ACTIVITY);
        preparedStatement.setString(1, card.getCardType());
        preparedStatement.setString(2, card.getName());
        preparedStatement.setString(3, workshopCode);
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
