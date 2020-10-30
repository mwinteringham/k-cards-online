package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Workshop {

    @JsonProperty
    private String code;
    @JsonProperty
    private String name;

    public Workshop() {
    }

    public Workshop(String name) {
        this.name = name;
    }

    public Workshop(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
