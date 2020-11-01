package com.automationintesting.model;

import com.automationintesting.service.CodeGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    CodeGenerator codeGenerator = new CodeGenerator();

    @JsonProperty
    private String attendeeCode;
    @JsonProperty
    private String cardType;
    @JsonProperty
    private String cardCode;

    public Card() {
    }

    public Card(String attendeeCode, String cardType, String cardCode) {
        this.attendeeCode = attendeeCode;
        this.cardType = cardType;
        this.cardCode = cardCode;
    }

    public Card(String attendeeCode, String cardType) {
        this.attendeeCode = attendeeCode;
        this.cardType = cardType;
        this.cardCode = codeGenerator.createCode();
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

    public String getCardCode() {
        return cardCode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "attendeeCode='" + attendeeCode + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardCode='" + cardCode + '\'' +
                '}';
    }
}
