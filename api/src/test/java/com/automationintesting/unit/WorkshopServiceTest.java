package com.automationintesting.unit;

import com.automationintesting.db.KCardDB;
import com.automationintesting.db.service.AttendeeListResult;
import com.automationintesting.db.service.WorkshopActivityResult;
import com.automationintesting.db.service.WorkshopResult;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.model.activity.Activity;
import com.automationintesting.model.activity.ActivityResponse;
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
        when(kCardDB.addCode(anyString(), eq("LEWT"))).thenReturn(true);

        WorkshopResult workshopResult = workshopService.createWorkshop("LEWT");

        assertThat(workshopResult.getHttpStatus(), equalTo(HttpStatus.CREATED));
        assertThat(workshopResult.getCode(), instanceOf(Workshop.class));
    }

    @Test
    public void createAttendeeTest() throws SQLException {
        Attendee attendee = new Attendee("Mary Jane");
        when(kCardDB.addAttendee(attendee, "abcdef")).thenReturn(true);

        HttpStatus httpStatus = workshopService.createAttendee(attendee, "abcdef");

        assertThat(httpStatus, equalTo(HttpStatus.CREATED));
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
        Card card = new Card("Jenny Sage", "red");

        when(kCardDB.addCardActivity(card, "hgfhgf")).thenReturn(true);

        HttpStatus cardCreationResult = workshopService.createCardActivity(card, "hgfhgf");

        assertThat(cardCreationResult, equalTo(HttpStatus.CREATED));
    }

    @Test
    public void getActivityListTest() throws SQLException {
        List<String> redList = new ArrayList<>(){{
            this.add("Jeff Biggs");
            this.add("Boz Jeans");
        }};

        Activity activity = new Activity(redList, null);

        when(kCardDB.getWorkshopActivity("FEWT")).thenReturn(activity);

        WorkshopActivityResult workshopActivityResult = workshopService.getWorkshopActivity("FEWT");

        assertThat(workshopActivityResult.getHttpStatus(), equalTo(HttpStatus.OK));
        assertThat(workshopActivityResult.getActivity(), instanceOf(ActivityResponse.class));
    }

}
