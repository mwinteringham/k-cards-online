package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomRequest {

    @JsonProperty
    private String workshopName;

    public RoomRequest() {
    }

    public RoomRequest(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

}
