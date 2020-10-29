package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomRequest {

    @JsonProperty
    private String name;

    public RoomRequest() {
    }

    public RoomRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
