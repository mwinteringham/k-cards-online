package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AttendeeList {

    @JsonProperty
    private List<Attendee> attendees;

    public AttendeeList() {
    }

    public AttendeeList(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return "AttendeeList{" +
                "attendees=" + attendees +
                '}';
    }
}
