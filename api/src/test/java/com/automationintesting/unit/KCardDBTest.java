package com.automationintesting.unit;

import com.automationintesting.db.KCardDB;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class KCardDBTest {

    private static KCardDB kCardDB;

    @BeforeClass
    public static void setupDb() throws SQLException {
        kCardDB = new KCardDB();
    }

    @Test
    public void storeWorkshopInDb() throws SQLException {
        String workshopCode = "abcdef";
        String workshopName = "LEWT";

        Boolean storeResult = kCardDB.addCode(workshopCode, workshopName);

        assertThat(storeResult, equalTo(true));
    }

    @Test
    public void storeAttendeeInDb() throws SQLException {
        Attendee attendee = new Attendee("Stacey Jones");

        Boolean storeResult = kCardDB.addAttendee(attendee, "abcdef");

        assertThat(storeResult, equalTo(true));
    }

    @Test
    public void returnAttendeesList() throws SQLException {
        Attendee attendee = new Attendee("Barry White");
        kCardDB.addAttendee(attendee, "xyzxyz");

        AttendeeList attendeeList = kCardDB.getAttendeesInWorkshop("xyzxyz");

        assertThat(attendeeList.getAttendees().size(), equalTo(1));
    }

}
