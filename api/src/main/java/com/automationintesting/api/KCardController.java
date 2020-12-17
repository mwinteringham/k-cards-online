package com.automationintesting.api;

import com.automationintesting.db.service.AttendeeJoinResult;
import com.automationintesting.db.service.AttendeeListResult;
import com.automationintesting.db.service.WorkshopActivityResult;
import com.automationintesting.db.service.WorkshopResult;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class KCardController {

    @Autowired
    private WorkshopService workshopService;

    @RequestMapping(value = "/workshop", method = RequestMethod.PUT)
    public ResponseEntity createWorkshop(@RequestBody Workshop workshop) throws SQLException {
        WorkshopResult workshopResult = workshopService.createWorkshop(workshop.getName());

        return ResponseEntity.status(workshopResult.getHttpStatus()).body(workshopResult.getCode());
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}/join", method = RequestMethod.POST)
    public ResponseEntity createAttendee(@PathVariable(value = "workshopcode") String workshopCode, @RequestBody Attendee attendee) throws SQLException {
        AttendeeJoinResult attendeeResult = workshopService.createAttendee(attendee, workshopCode);

        return ResponseEntity.status(attendeeResult.getHttpStatus()).body(attendeeResult.getAttendee());
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}/attendees", method = RequestMethod.GET)
    public ResponseEntity getAttendees(@PathVariable(value = "workshopcode") String workshopCode) throws SQLException {
        AttendeeListResult attendeeListResult = workshopService.getAttendeesInWorkshop(workshopCode);

        return ResponseEntity.status(attendeeListResult.getHttpStatus()).body(attendeeListResult.getAttendees());
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}/card", method = RequestMethod.POST)
    public ResponseEntity createCard(@PathVariable(value = "workshopcode") String workshopCode, @RequestBody Card card) throws SQLException {
        HttpStatus cardCreationResult = workshopService.createCard(card, workshopCode);

        return ResponseEntity.status(cardCreationResult).build();
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}/activity", method = RequestMethod.GET)
    public ResponseEntity getActivity(@PathVariable(value = "workshopcode") String workshopCode) throws SQLException {
        WorkshopActivityResult workshopActivityResult = workshopService.getWorkshopActivity(workshopCode);

        return ResponseEntity.status(workshopActivityResult.getHttpStatus()).body(workshopActivityResult.getActivity());
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}/leave", method = RequestMethod.DELETE)
    public ResponseEntity removeAttendee(@PathVariable(value = "workshopcode") String workshopcode, @RequestBody Attendee attendee) throws SQLException {
        HttpStatus removalResult = workshopService.removeAttendee(attendee.getCode(), workshopcode);

        return ResponseEntity.status(removalResult).build();
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}/card/{cardcode:.+}", method = RequestMethod.DELETE)
    public ResponseEntity removeCard(@PathVariable(value = "cardcode") String cardcode) throws SQLException {
        HttpStatus removalResult = workshopService.removeCard(cardcode);

        return ResponseEntity.status(removalResult).build();
    }

    @RequestMapping(value = "/workshop/{workshopcode:.+}", method = RequestMethod.DELETE)
    public ResponseEntity removeAttendee(@PathVariable(value = "workshopcode") String workshopcode) throws SQLException {
        HttpStatus removalResult = workshopService.removeWorkshop(workshopcode);

        return ResponseEntity.status(removalResult).build();
    }

}
