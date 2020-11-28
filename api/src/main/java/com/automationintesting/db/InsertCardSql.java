package com.automationintesting.db;

import com.automationintesting.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCardSql {

    private final Connection connection;
    private final Card card;
    private final String name;
    private final String workshopCode;

    public InsertCardSql(Connection connection, Card card, String name, String workshopCode) {
        this.connection = connection;
        this.card = card;
        this.name = name;
        this.workshopCode = workshopCode;
    }

    public PreparedStatement getPreparedStatementForStandardCard() throws SQLException {
        final String CREATE_STANDARD_CARD = "INSERT INTO CARDS (cardcode, cardtype, attendeecode, name, workshopcode) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_STANDARD_CARD);
        preparedStatement.setString(1, card.getCardCode());
        preparedStatement.setString(2, card.getCardType());
        preparedStatement.setString(3, card.getAttendeeCode());
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, workshopCode);

        return preparedStatement;
    }

    public PreparedStatement getPreparedStatementForYellowCard(String greenCardCode) throws SQLException {
        final String CREATE_YELLOW_CARD = "INSERT INTO CARDS (cardcode, cardtype, attendeecode, relatedcardcode, name, workshopcode) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_YELLOW_CARD);
        preparedStatement.setString(1, card.getCardCode());
        preparedStatement.setString(2, card.getCardType());
        preparedStatement.setString(3, card.getAttendeeCode());
        preparedStatement.setString(4, greenCardCode);
        preparedStatement.setString(5, name);
        preparedStatement.setString(6, workshopCode);

        return preparedStatement;
    }

}
