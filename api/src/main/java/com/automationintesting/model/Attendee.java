package com.automationintesting.model;

import com.automationintesting.service.CodeGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attendee {

    @JsonProperty
    private String name;
    @JsonProperty
    private String code;

    private CodeGenerator codeGenerator = new CodeGenerator();

    public Attendee() {}

    public Attendee(String name) {
        this.name = name;
        code = codeGenerator.createCode();
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
}
