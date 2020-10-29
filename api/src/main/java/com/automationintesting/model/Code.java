package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code {

    @JsonProperty
    private String code;

    public Code() {
    }

    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
