package com.automationintesting.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class WorkshopService {

    @Autowired
    private KCardDB kCardDB;

    @Autowired
    public WorkshopService() {
    }

    public WorkshopResult createWorkshop(String workshopName) throws SQLException {
        if(kCardDB.queryWorkshop(workshopName)){
            String workshopCode = kCardDB.getWorkshopCode(workshopName);

            return new WorkshopResult(HttpStatus.OK, new Workshop(workshopCode, workshopName));
        } else {
            UUID uuid = UUID.randomUUID();

            if(kCardDB.addWorkshop(uuid.toString(), workshopName)){
                return new WorkshopResult(HttpStatus.CREATED, new Workshop(uuid.toString(), workshopName));
            } else {
                return new WorkshopResult(HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    public AttendeeJoinResult createAttendee(Attendee attendee, String workshopCode) throws SQLException {
        if(kCardDB.doesWorkshopExist(workshopCode)){
            UUID uuid = UUID.randomUUID();
            attendee.setCode(uuid.toString());

            if(kCardDB.addAttendee(attendee, workshopCode)){
                return new AttendeeJoinResult(HttpStatus.CREATED, attendee);
            } else {
                return new AttendeeJoinResult(HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new AttendeeJoinResult(HttpStatus.NOT_FOUND);
        }
    }

    public AttendeeListResult getAttendeesInWorkshop(String workshopCode) throws SQLException {
        AttendeeList attendeeList = kCardDB.getAttendeesInWorkshop(workshopCode);
        return new AttendeeListResult(HttpStatus.OK, attendeeList);
    }

    public HttpStatus createCard(Card card, String workshopCode) throws SQLException {
        if(kCardDB.validateCardCreation(card, workshopCode)){
            UUID uuid = UUID.randomUUID();
            card.setCardCode(uuid.toString());

            if(kCardDB.addCard(card, workshopCode)){
                return HttpStatus.CREATED;
            } else {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public WorkshopActivityResult getWorkshopActivity(String workshopCode) throws SQLException {
        Activity activity = kCardDB.getWorkshopActivity(workshopCode);
        ActivityResponse activityResponse = new ActivityResponse(activity);

        return new WorkshopActivityResult(HttpStatus.OK, activityResponse);
    }

    public HttpStatus removeAttendee(String attendeeCode, String workshopCode) throws SQLException {
        if(kCardDB.removeAttendee(attendeeCode, workshopCode)){
            if(kCardDB.removeAttendeesCards(attendeeCode, workshopCode)){
                return HttpStatus.ACCEPTED;
            } else {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus removeCard(String cardCode) throws SQLException {
        if(kCardDB.removeCard(cardCode)){
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    public HttpStatus removeWorkshop(String workshopCode) throws SQLException {
        if(kCardDB.removeWorkshop(workshopCode)){
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
