package com.automationintesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    @JsonProperty
    private java.lang.String name;
    @JsonProperty
    private String string;

    public Card() {
    }

    public Card(String name, String string) {
        this.name = name;
        this.string = string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return string;
    }

    public void setCardType(String string) {
        this.string = string;
    }
}
