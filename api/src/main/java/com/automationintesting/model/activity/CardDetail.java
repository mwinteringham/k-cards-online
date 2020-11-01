package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardDetail {

    @JsonProperty
    private String code;
    @JsonProperty
    private String name;

    public CardDetail() {
    }

    public CardDetail(String code, String name) {
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

    @Override
    public String toString() {
        return "CardDetail{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
