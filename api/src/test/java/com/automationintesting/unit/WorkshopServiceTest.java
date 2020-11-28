package com.automationintesting.unit;

import com.automationintesting.db.KCardDB;
import com.automationintesting.db.service.AttendeeJoinResult;
import com.automationintesting.db.service.AttendeeListResult;
import com.automationintesting.db.service.WorkshopActivityResult;
import com.automationintesting.db.service.WorkshopResult;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.model.activity.Activity;
import com.automationintesting.model.activity.ActivityResponse;
import com.automationintesting.model.activity.CardDetail;
import com.automationintesting.service.WorkshopService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class WorkshopServiceTest {

    @Mock
    private KCardDB kCardDB;

    @Autowired
    @InjectMocks
    private WorkshopService workshopService;

    @Before
    public void initialiseMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createWorkshopTest() throws SQLException {
        when(kCardDB.addWorkshop(anyString(), eq("LEWT"))).thenReturn(true);

        WorkshopResult workshopResult = workshopService.createWorkshop("LEWT");

        assertThat(workshopResult.getHttpStatus(), equalTo(HttpStatus.CREATED));
        assertThat(workshopResult.getCode(), instanceOf(Workshop.class));
    }

    @Test
    public void createAttendeeTest() throws SQLException {
        Attendee attendee = new Attendee("Mary Jane");
        when(kCardDB.doesWorkshopExist("abcdef")).thenReturn(true);
        when(kCardDB.addAttendee(attendee, "abcdef")).thenReturn(true);

        AttendeeJoinResult attendeeJoinResult = workshopService.createAttendee(attendee, "abcdef");

        assertThat(attendeeJoinResult.getHttpStatus(), equalTo(HttpStatus.CREATED));
        assertThat(attendeeJoinResult.getAttendee(), instanceOf(Attendee.class));
    }

    @Test
    public void noWorkshopToJoinTest() throws SQLException {
        Attendee attendee = new Attendee("Mary Jane");
        when(kCardDB.doesWorkshopExist("fedcba")).thenReturn(false);

        AttendeeJoinResult attendeeJoinResult = workshopService.createAttendee(attendee, "fedcba");

        assertThat(attendeeJoinResult.getHttpStatus(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAttendeeListTest() throws SQLException {
        List<Attendee> listToAdd = new ArrayList<>() {{
            this.add(new Attendee("Henry Brown"));
        }};
        AttendeeList attendeeList = new AttendeeList(listToAdd);

        when(kCardDB.getAttendeesInWorkshop("abcdef")).thenReturn(attendeeList);

        AttendeeListResult attendeeListResult = workshopService.getAttendeesInWorkshop("abcdef");

        assertThat(attendeeListResult.getHttpStatus(), equalTo(HttpStatus.OK));
        assertThat(attendeeListResult.getAttendees(), instanceOf(AttendeeList.class));
    }

    @Test
    public void createCardActivityTest() throws SQLException {
        Attendee attendee = new Attendee("Jenny Sage");
        Card card = new Card(attendee.getCode(), "red");

        when(kCardDB.addCardActivity(card, "hgfhgf")).thenReturn(true);
        when(kCardDB.validateCardCreation(card, "hgfhgf")).thenReturn(true);

        HttpStatus cardCreationResult = workshopService.createCard(card, "hgfhgf");

        assertThat(cardCreationResult, equalTo(HttpStatus.CREATED));
    }

    @Test
    public void getActivityListTest() throws SQLException {
        List<CardDetail> redList = new ArrayList<>(){{
            this.add(new CardDetail("abc123","Jeff Biggs"));
            this.add(new CardDetail("123abc", "Boz Jeans"));
        }};

        Activity activity = new Activity(redList, null);

        when(kCardDB.getWorkshopActivity("FEWT")).thenReturn(activity);

        WorkshopActivityResult workshopActivityResult = workshopService.getWorkshopActivity("FEWT");

        assertThat(workshopActivityResult.getHttpStatus(), equalTo(HttpStatus.OK));
        assertThat(workshopActivityResult.getActivity(), instanceOf(ActivityResponse.class));
    }

    @Test
    public void deleteAttendeeTest() throws SQLException {
        Attendee attendee = new Attendee("Bill James");

        when(kCardDB.removeAttendee(attendee.getCode(), "asdfgh")).thenReturn(true);
        when(kCardDB.removeAttendeesCards(attendee.getCode(), "asdfgh")).thenReturn(true);

        HttpStatus httpStatus = workshopService.removeAttendee(attendee.getCode(), "asdfgh");

        assertThat(httpStatus, equalTo(HttpStatus.ACCEPTED));
    }

    @Test
    public void deleteAttendeeWhoDoesNotExistTest() throws SQLException {
        Attendee attendee = new Attendee("Bill James");

        when(kCardDB.removeAttendee(attendee.getCode(), "asdfgh")).thenReturn(false);

        HttpStatus httpStatus = workshopService.removeAttendee(attendee.getCode(), "asdfgh");

        assertThat(httpStatus, equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void deleteCards() throws SQLException {
        when(kCardDB.removeCard("abcdef")).thenReturn(true);
        when(kCardDB.removeCard("poiuyt")).thenReturn(true);

        List<String> cards = new ArrayList<>(){{
           this.add("abcdef");
           this.add("poiuyt");
        }};

        HttpStatus httpStatus = workshopService.removeCard(cards);

        assertThat(httpStatus, equalTo(HttpStatus.ACCEPTED));
    }

    @Test
    public void deleteWorkshop() throws SQLException {
        when(kCardDB.removeWorkshop("abcdef")).thenReturn(true);

        HttpStatus httpStatus = workshopService.removeWorkshop("abcdef");

        assertThat(httpStatus, equalTo(HttpStatus.ACCEPTED));
    }

}
