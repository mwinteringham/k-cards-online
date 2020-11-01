package com.automationintesting.model;

import com.automationintesting.service.CodeGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attendee {

    @JsonProperty
    private String name;
    @JsonProperty
    private String code;

    public Attendee() {}

    public Attendee(String name, String code) {
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "name='" + name + '\'' +
                ", code='" + code +
                '}';
    }
}
