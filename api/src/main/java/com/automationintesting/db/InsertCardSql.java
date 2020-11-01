package com.automationintesting.db;

import com.automationintesting.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCardSql {

    private final PreparedStatement preparedStatement;

    public InsertCardSql(Connection connection, Card card, String name, String workshopCode) throws SQLException {
        final String CREATE_ACTIVITY = "INSERT INTO CARDS (cardcode, cardtype, attendeecode, name, workshopcode) VALUES (?, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(CREATE_ACTIVITY);
        preparedStatement.setString(1, card.getCardCode());
        preparedStatement.setString(2, card.getCardType());
        preparedStatement.setString(3, card.getAttendeeCode());
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, workshopCode);
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
