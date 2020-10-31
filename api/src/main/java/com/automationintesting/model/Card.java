package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    @JsonProperty
    private String name;
    @JsonProperty
    private String attendeeCode;
    @JsonProperty
    private String cardType;

    public Card() {
    }

    public Card(String name, String attendeeCode, String cardType) {
        this.name = name;
        this.attendeeCode = attendeeCode;
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAttendeeCode() {
        return attendeeCode;
    }

    public void setAttendeeCode(String attendeeCode) {
        this.attendeeCode = attendeeCode;
    }
}
