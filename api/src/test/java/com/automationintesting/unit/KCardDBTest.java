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
import java.util.ArrayList;
import java.util.List;

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
        Attendee attendee = new Attendee("Amy Lee", "fedcba");
        Card card = new Card(attendee.getCode(), "red");

        kCardDB.addAttendee(attendee, "jsdjsd");

        Boolean storeResult = kCardDB.addCard(card, "jsdjsd");

        assertThat(storeResult, equalTo(true));
    }

    @Test
    public void returnWorkshopActivity() throws SQLException {
        List<Attendee> attendees = new ArrayList<>(){{
            this.add(new Attendee("Amy Lee","zxcvbn"));
            this.add(new Attendee("Stuart Jones","nbvcxz"));
            this.add(new Attendee("Sam Jones","qwerty"));
        }};

        for(Attendee attendee : attendees){
            kCardDB.addAttendee(attendee, "jsdjsd");
        }

        Card redCard = new Card(attendees.get(0).getCode(), "red", "abcd");
        Card greenCard = new Card(attendees.get(1).getCode(), "green", "efgh");
        Card yellowCard = new Card(attendees.get(2).getCode(), "yellow", "hijkl");

        kCardDB.addCard(redCard, "BEWT");
        kCardDB.addCard(greenCard, "BEWT");
        kCardDB.addCard(yellowCard, "BEWT");

        Activity activity = kCardDB.getWorkshopActivity("BEWT");

        Approvals.verifyAsJson(activity);
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
        Attendee attendee = new Attendee("James Salmon","tyuiop");
        kCardDB.addAttendee(attendee, "qwerty");

        Boolean attendeeRemoved = kCardDB.removeAttendee(attendee.getCode(),"qwerty");

        assertThat(attendeeRemoved, equalTo(true));
    }

    @Test
    public void removeAttendeesCards() throws SQLException {
        Attendee attendee = new Attendee("Boz Badger", "abcdef");

        kCardDB.addAttendee(attendee, "poiuyt");

        Card redCard = new Card(attendee.getCode(), "red");
        Card greenCard = new Card(attendee.getCode(), "green");
        Card yellowCard = new Card(attendee.getCode(), "yellow");

        kCardDB.addCard(redCard, "poiuyt");
        kCardDB.addCard(greenCard, "poiuyt");
        kCardDB.addCard(yellowCard, "poiuyt");

        Boolean cardsRemoved = kCardDB.removeAttendeesCards(attendee.getCode(), "poiuyt");

        assertThat(cardsRemoved, equalTo(true));
    }

    @Test
    public void removeCardInWorkshop() throws SQLException {
        Attendee attendee = new Attendee("Boz Badger","poiuyt");
        kCardDB.addAttendee(attendee, "mnbvcx");

        Card redCard = new Card(attendee.getCode(), "red", "abc");

        kCardDB.addCard(redCard, "mnbvc");

        Boolean cardRemoved = kCardDB.removeCard(redCard.getCardCode());

        assertThat(cardRemoved, equalTo(true));
    }

    @Test
    public void removeWorkshopAndRelatedCard() throws SQLException {
        kCardDB.addWorkshop("zxcvbn", "HEWT");

        Attendee attendee = new Attendee("Boz Badger","lkjhgf");
        kCardDB.addAttendee(attendee, "zxcvbn");

        Card redCard = new Card(attendee.getCode(), "red");
        Card greenCard = new Card(attendee.getCode(), "green");

        kCardDB.addCard(redCard,"zxcvbn");
        kCardDB.addCard(greenCard,"zxcvbn");

        Boolean workshopRemoved = kCardDB.removeWorkshop("zxcvgn");

        assertThat(workshopRemoved, equalTo(true));
    }

    @Test
    public void validateNegativeCardCreationTest() throws SQLException {
        kCardDB.addWorkshop("ijsdfh", "SEWT");

        Attendee attendee = new Attendee("Jim Sainz","qidksm");
        kCardDB.addAttendee(attendee, "ijsdfh");

        Card yellowCard = new Card(attendee.getCode(), "yellow");

        Boolean canCreateCard = kCardDB.validateCardCreation(yellowCard, "ijsdfh");

        assertThat(canCreateCard, equalTo(false));
    }

    @Test
    public void validatePositiveCardCreationTest() throws SQLException {
        kCardDB.addWorkshop("lapqmx", "SEWT");

        Attendee attendee = new Attendee("Jim Sainz","qidksm");
        kCardDB.addAttendee(attendee, "lapqmx");

        Card greenCard = new Card(attendee.getCode(), "green");
        Card yellowCard = new Card(attendee.getCode(), "yellow");

        kCardDB.addCard(greenCard, "lapqmx");

        Boolean canCreateCard = kCardDB.validateCardCreation(yellowCard, "lapqmx");

        assertThat(canCreateCard, equalTo(true));
    }

    @Test
    public void queryWorkshopName() throws SQLException {
        kCardDB.addWorkshop("paiskd", "LAWT");

        Boolean workshopExists = kCardDB.queryWorkshop("LAWT");

        assertThat(workshopExists, equalTo(true));
    }

    @Test
    public void getWorkshopCode() throws SQLException {
        kCardDB.addWorkshop("123456", "JAWT");

        String workshopCode = kCardDB.getWorkshopCode("JAWT");

        assertThat(workshopCode, equalTo("123456"));
    }

}
