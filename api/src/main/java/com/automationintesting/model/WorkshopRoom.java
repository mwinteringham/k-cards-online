package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkshopRoom {

    @JsonProperty
    private String code;
    @JsonProperty
    private String workshopName;

    public WorkshopRoom() {
    }

    public WorkshopRoom(String code, String workshopName) {
        this.code = code;
        this.workshopName = workshopName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
}
