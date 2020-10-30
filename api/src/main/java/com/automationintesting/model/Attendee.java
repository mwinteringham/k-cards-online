package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attendee {

    @JsonProperty
    private String name;

    public Attendee() {
    }

    public Attendee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
