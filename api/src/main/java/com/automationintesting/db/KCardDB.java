package com.automationintesting.db;

import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.activity.Activity;
import com.automationintesting.model.activity.ActivityThread;
import com.automationintesting.model.activity.CardDetail;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class KCardDB {

    private final Connection connection;

    private final String CREATE_WORKSHOPS_TABLE = "CREATE TABLE IF NOT EXISTS WORKSHOPS ( id int NOT NULL AUTO_INCREMENT, code varchar(36), workshopName varchar(255), created TIMESTAMP AS CURRENT_TIMESTAMP(), primary key (id))";
    private final String CREATE_ATTENDEE_TABLE = "CREATE TABLE IF NOT EXISTS ATTENDEES ( attendee_id int NOT NULL AUTO_INCREMENT, attendeecode varchar(36), name varchar(255), workshopcode varchar(36), created TIMESTAMP AS CURRENT_TIMESTAMP(), primary key (attendee_id))";
    private final String CREATE_CARD_TABLE = "CREATE TABLE IF NOT EXISTS CARDS (id int NOT NULL AUTO_INCREMENT, cardcode varchar(36), relatedcardcode varchar(36), cardtype varchar(6), attendeecode varchar(36), name varchar(255), workshopcode varchar(36), created TIMESTAMP AS CURRENT_TIMESTAMP(), primary key (id))";

    private final String SELECT_ATTENDEE = "SELECT name FROM ATTENDEES WHERE attendeecode = ?";
    private final String SELECT_ATTENDEES = "SELECT * FROM ATTENDEES WHERE workshopcode = ?";
    private final String SELECT_CARDS = "SELECT * FROM CARDS WHERE workshopcode = ?";
    private final String SELECT_GREEN_CARDS = "SELECT COUNT(*) AS total FROM CARDS WHERE workshopcode = ? AND cardtype = 'green'";
    private final String SELECT_CARDS_FROM_ATTENDEE = "SELECT COUNT(*) AS total FROM CARDS WHERE attendeecode = ? AND workshopcode = ?";
    private final String SELECT_CARD_COUNT_BY_WORKSHOP = "SELECT COUNT(*) AS total FROM CARDS WHERE workshopcode = ?";
    private final String SELECT_WORKSHOP_COUNTS = "SELECT COUNT(*) AS total FROM WORKSHOPS WHERE code = ?";
    private final String SELECT_WORKSHOP_ATTENDEE = "SELECT COUNT(*) AS total FROM ATTENDEES WHERE attendeecode = ? AND workshopcode = ?";
    private final String SELECT_ATTENDEE_COUNT_BY_WORKSHOP = "SELECT COUNT(*) AS total FROM ATTENDEES WHERE workshopcode = ?";
    private final String SELECT_WORKSHOP_COUNT = "SELECT COUNT(*) AS total FROM WORKSHOPS where workshopName = ?";
    private final String SELECT_WORKSHOP_CODE = "SELECT * FROM WORKSHOPS where workshopName = ?";

    private final String DELETE_ATTENDEE = "DELETE FROM ATTENDEES WHERE attendeecode = ? AND workshopcode = ?";
    private final String DELETE_ATTENDEE_CARDS = "DELETE FROM CARDS WHERE attendeecode = ? AND workshopcode = ?";
    private final String DELETE_CARD_BY_CARD_CODE = "DELETE FROM CARDS WHERE cardcode = ? OR relatedcardcode = ?";
    private final String DELETE_CARD_BY_WORKSHOP = "DELETE FROM CARDS WHERE workshopcode = ?";
    private final String DELETE_WORKSHOP = "DELETE FROM WORKSHOPS WHERE code = ?";
    private final String DELETE_ATTENDEES_BY_WORKSHOP = "DELETE FROM ATTENDEES WHERE workshopcode = ?";

    public KCardDB() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:kcard;MODE=MySQL");
        ds.setUser("user");
        ds.setPassword("password");
        connection = ds.getConnection();

//        Server.createTcpServer("-tcpPort", "9091", "-tcpAllowOthers").start();

        connection.prepareStatement(CREATE_WORKSHOPS_TABLE).executeUpdate();
        connection.prepareStatement(CREATE_ATTENDEE_TABLE).executeUpdate();
        connection.prepareStatement(CREATE_CARD_TABLE).executeUpdate();
    }

    public Boolean addWorkshop(String code, String workshopName) throws SQLException {
        InsertWorkshopSql insertWorkshopSql = new InsertWorkshopSql(connection, code, workshopName);
        PreparedStatement createPs = insertWorkshopSql.getPreparedStatement();

        return createPs.executeUpdate() > 0;
    }

    public Boolean addAttendee(Attendee attendee, String workshopCode) throws SQLException {
        InsertAttendeeSql insertAttendeeSql = new InsertAttendeeSql(connection, attendee, workshopCode);
        PreparedStatement createPs = insertAttendeeSql.getPreparedStatement();

        return createPs.executeUpdate() > 0;
    }

    public AttendeeList getAttendeesInWorkshop(String workshopCode) throws SQLException {
        List<Attendee> listToReturn = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(SELECT_ATTENDEES);
        ps.setString(1, workshopCode);

        ResultSet results = ps.executeQuery();

        while(results.next()){
            listToReturn.add(new Attendee(results.getString("name"), results.getString("attendeecode")));
        }

        return new AttendeeList(listToReturn);
    }

    public Boolean addCard(Card card, String workshopCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_ATTENDEE);
        ps.setString(1, card.getAttendeeCode());

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        PreparedStatement createPs;

        if(card.getCardType().contains("yellow")){
            PreparedStatement latestPs = connection.prepareStatement("SELECT * FROM CARDS WHERE cardtype = 'green' ORDER BY created");

            ResultSet latestResultSet = latestPs.executeQuery();
            latestResultSet.next();

            InsertCardSql insertCardSql = new InsertCardSql(connection, card, resultSet.getString("name"), workshopCode);
            createPs = insertCardSql.getPreparedStatementForYellowCard(latestResultSet.getString("cardcode"));
        } else {
            InsertCardSql insertCardSql = new InsertCardSql(connection, card, resultSet.getString("name"), workshopCode);
            createPs = insertCardSql.getPreparedStatementForStandardCard();
        }

        return createPs.executeUpdate() > 0;
    }

    public Activity getWorkshopActivity(String workshopCode) throws SQLException {
        List<CardDetail> redList = new ArrayList<>();
        List<ActivityThread> activityThreadList = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(SELECT_CARDS);
        ps.setString(1, workshopCode);

        ResultSet results = ps.executeQuery();

        while(results.next()){
            switch (results.getString("cardtype")) {
                case "red":
                    redList.add(new CardDetail(results.getString("cardcode"), results.getString("name")));
                    break;
                case "green":
                    activityThreadList.add(new ActivityThread(new CardDetail(results.getString("cardcode"), results.getString("name"))));
                    break;
                case "yellow":
                    if(!activityThreadList.isEmpty()){
                        activityThreadList.get(0).addToSubThread(new CardDetail(results.getString("cardcode"), results.getString("name")));
                    }
                    break;
            }
        }

        return new Activity(redList, activityThreadList);
    }

    public Boolean doesWorkshopExist(String workshopCode) throws SQLException {
       int count = getCount(SELECT_WORKSHOP_COUNTS, workshopCode);

       return count > 0;
    }

    public Boolean removeAttendee(String attendeeCode, String workshopCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE_ATTENDEE);
        ps.setString(1, attendeeCode);
        ps.setString(2, workshopCode);

        int resultSet = ps.executeUpdate();
        return resultSet == 1;
    }

    public Boolean removeAttendeesCards(String attendeeCode, String workshopCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE_ATTENDEE_CARDS);
        ps.setString(1, attendeeCode);
        ps.setString(2, workshopCode);
        ps.executeUpdate();

        PreparedStatement ps2 = connection.prepareStatement(SELECT_CARDS_FROM_ATTENDEE);
        ps2.setString(1, attendeeCode);
        ps2.setString(2, workshopCode);

        ResultSet results = ps2.executeQuery();
        results.next();

        return results.getInt("total") == 0;
    }

    public Boolean removeCard(String cardCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE_CARD_BY_CARD_CODE);
        ps.setString(1, cardCode);
        ps.setString(2, cardCode);

        int resultSet = ps.executeUpdate();
        return resultSet == 1;
    }

    public Boolean removeWorkshop(String workshopCode) throws SQLException {
        PreparedStatement attendeeDeletePs = connection.prepareStatement(DELETE_ATTENDEES_BY_WORKSHOP);
        attendeeDeletePs.setString(1, workshopCode);
        attendeeDeletePs.execute();

        PreparedStatement cardsDeletePs = connection.prepareStatement(DELETE_CARD_BY_WORKSHOP);
        cardsDeletePs.setString(1, workshopCode);
        cardsDeletePs.execute();

        PreparedStatement workshopDeletePs = connection.prepareStatement(DELETE_WORKSHOP);
        workshopDeletePs.setString(1, workshopCode);
        workshopDeletePs.executeUpdate();

        int attendeeCount = getCount(SELECT_ATTENDEE_COUNT_BY_WORKSHOP, workshopCode);
        int cardCount = getCount(SELECT_CARD_COUNT_BY_WORKSHOP, workshopCode);
        int workshopCount = getCount(SELECT_WORKSHOP_COUNTS, workshopCode);

        return (attendeeCount + cardCount + workshopCount) == 0;
    }

    public Boolean validateCardCreation(Card card, String workshopCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_WORKSHOP_ATTENDEE);
        ps.setString(1, card.getAttendeeCode());
        ps.setString(2, workshopCode);

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        if(resultSet.getInt("total") == 1){
            if(card.getCardType().endsWith("yellow")){
                int greenCardCount = getCount(SELECT_GREEN_CARDS, workshopCode);

                return greenCardCount > 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean queryWorkshop(String workshopName) throws SQLException {
        int total = getCount(SELECT_WORKSHOP_COUNT, workshopName);

        return total > 0;
    }

    public String getWorkshopCode(String workshopCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_WORKSHOP_CODE);
        ps.setString(1, workshopCode);

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        return resultSet.getString("code");
    }

    private int getCount(String sql, String workshopCode) throws SQLException {
        PreparedStatement cardCountPs = connection.prepareStatement(sql);
        cardCountPs.setString(1, workshopCode);

        ResultSet cardCountResultSet = cardCountPs.executeQuery();
        cardCountResultSet.next();

        return cardCountResultSet.getInt("total");
    }

}
