package com.automationintesting.db.service;

import com.automationintesting.model.AttendeeList;
import org.springframework.http.HttpStatus;

public class AttendeeListResult {

    private HttpStatus httpStatus;

    private AttendeeList attendeeList;

    public AttendeeListResult(HttpStatus httpStatus, AttendeeList attendeeList) {
        this.httpStatus = httpStatus;
        this.attendeeList = attendeeList;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public AttendeeList getAttendees() {
        return attendeeList;
    }

}
