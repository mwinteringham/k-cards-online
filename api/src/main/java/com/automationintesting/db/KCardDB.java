package com.automationintesting.db;

import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.activity.Activity;
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

    private Connection connection;

    private final String CREATE_WORKSHOPS_TABLE = "CREATE table WORKSHOPS ( code varchar(6), workshopName varchar(255), primary key (code))";
    private final String CREATE_ATTENDEE_TABLE = "CREATE table ATTENDEES ( attendee_id int NOT NULL AUTO_INCREMENT, name varchar(255), workshopcode varchar(6), primary key (attendee_id))";
    private final String CREATE_ACTIVITY_TABLE = "CREATE table ACTIVITIES ( activity_id int NOT NULL AUTO_INCREMENT, cardtype varchar(6), name varchar(255), workshopcode varchar(6), primary key (activity_id))";

    public KCardDB() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:kcard;MODE=MySQL");
        ds.setUser("user");
        ds.setPassword("password");
        connection = ds.getConnection();

        connection.prepareStatement(CREATE_WORKSHOPS_TABLE).executeUpdate();
        connection.prepareStatement(CREATE_ATTENDEE_TABLE).executeUpdate();
        connection.prepareStatement(CREATE_ACTIVITY_TABLE).executeUpdate();
    }

    public Boolean addCode(String code, String workshopName) throws SQLException {
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
        String sql = "SELECT * FROM ATTENDEES WHERE workshopcode = '" + workshopCode + "'";

        ResultSet results = connection.prepareStatement(sql).executeQuery();

        while(results.next()){
            listToReturn.add(new Attendee(results.getString("name")));
        }

        return new AttendeeList(listToReturn);
    }

    public Boolean addCardActivity(Card card, String workshopCode) throws SQLException {
        InsertActivitySql insertActivitySql = new InsertActivitySql(connection, card, workshopCode);
        PreparedStatement createPs = insertActivitySql.getPreparedStatement();

        return createPs.executeUpdate() > 0;
    }

    public Activity getWorkshopActivity(String workshopCode) throws SQLException {
        List<String> redList = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVITIES WHERE workshopcode = '" + workshopCode + "'";

        ResultSet results = connection.prepareStatement(sql).executeQuery();

        while(results.next()){
            if(results.getString("cardtype").contentEquals("red")){
                redList.add(results.getString("name"));
            }
        }

        return new Activity(redList, null);
    }
}
