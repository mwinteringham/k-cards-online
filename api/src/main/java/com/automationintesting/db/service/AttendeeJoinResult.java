package com.automationintesting.db.service;

import com.automationintesting.model.Attendee;
import org.springframework.http.HttpStatus;

public class AttendeeJoinResult {

    private HttpStatus httpStatus;
    private Attendee attendee;

    public AttendeeJoinResult(HttpStatus httpStatus, Attendee attendee) {
        this.httpStatus = httpStatus;
        this.attendee = attendee;
    }

    public AttendeeJoinResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Attendee getAttendee() {
        return attendee;
    }
}
