package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ActivityThread {

    @JsonProperty
    private CardDetail cardDetail;
    @JsonProperty
    private List<CardDetail> subThread = new ArrayList<>();

    public ActivityThread() {
    }

    public ActivityThread(CardDetail cardDetail) {
        this.cardDetail = cardDetail;
    }

    public CardDetail getCardDetail() {
        return cardDetail;
    }

    public void setCardDetail(CardDetail cardDetail) {
        this.cardDetail = cardDetail;
    }

    public List<CardDetail> getSubThread() {
        return subThread;
    }

    public void addToSubThread(CardDetail name){
        subThread.add(name);
    }

    @Override
    public String toString() {
        return "ActivityThread{" +
                "cardDetail=" + cardDetail +
                ", subThread=" + subThread +
                '}';
    }
}
