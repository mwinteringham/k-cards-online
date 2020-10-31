package com.automationintesting.unit;

import com.automationintesting.db.KCardDB;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.activity.Activity;
import org.approvaltests.Approvals;
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

        Boolean storeResult = kCardDB.addWorkshop(workshopCode, workshopName);

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

    @Test
    public void storeCardActivity() throws SQLException {
        Attendee attendee = new Attendee("Amy Lee");
        Card card = new Card(attendee.getName(), attendee.getCode(), "red");

        Boolean storeResult = kCardDB.addCardActivity(card, "jsdjsd");

        assertThat(storeResult, equalTo(true));
    }

    @Test
    public void returnWorkshopActivity() throws SQLException {
        Attendee attendee1 = new Attendee("Amy Lee");
        Attendee attendee2 = new Attendee("Stuart Jones");
        Attendee attendee3 = new Attendee("Sam Jones");

        Card redCard = new Card(attendee1.getName(), attendee1.getCode(), "red");
        Card greenCard = new Card(attendee2.getName(), attendee2.getCode(), "green");
        Card yellowCard = new Card(attendee3.getName(), attendee3.getCode(), "yellow");

        kCardDB.addCardActivity(redCard, "BEWT");
        kCardDB.addCardActivity(greenCard, "BEWT");
        kCardDB.addCardActivity(yellowCard, "BEWT");

        Activity activity = kCardDB.getWorkshopActivity("BEWT");

        Approvals.verify(activity);
    }

    @Test
    public void checkWorkshopExists() throws SQLException {
        kCardDB.addWorkshop("abcdef", "FEWT");

        Boolean workshopExists = kCardDB.doesWorkshopExist("abcdef");

        assertThat(workshopExists, equalTo(true));
    }

    @Test
    public void checkWorkshopDoesNotExist() throws SQLException {
        Boolean workshopExists = kCardDB.doesWorkshopExist("nowtef");

        assertThat(workshopExists, equalTo(false));
    }

    @Test
    public void removeAttendeeFromWorkshop() throws SQLException {
        Attendee attendee = new Attendee("James Salmon");
        kCardDB.addAttendee(attendee, "qwerty");

        Boolean attendeeRemoved = kCardDB.removeAttendee(attendee.getCode(),"qwerty");

        assertThat(attendeeRemoved, equalTo(true));
    }

    @Test
    public void removeAttendeesCards() throws SQLException {
        Attendee attendee = new Attendee("Boz Badger");

        Card redCard = new Card("Boz Badger", attendee.getCode(), "red");
        Card greenCard = new Card("Boz Badger", attendee.getCode(), "green");
        Card yellowCard = new Card("Boz Badger", attendee.getCode(), "yellow");

        kCardDB.addCardActivity(redCard, "poiuyt");
        kCardDB.addCardActivity(greenCard, "poiuyt");
        kCardDB.addCardActivity(yellowCard, "poiuyt");

        Boolean cardsRemoved = kCardDB.removeAttendeesCards(attendee.getCode(), "poiuyt");

        assertThat(cardsRemoved, equalTo(true));
    }
}
