package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Activity {

    @JsonProperty
    private List<CardDetail> reds;
    @JsonProperty
    private List<ActivityThread> threads;

    public Activity() {
    }

    public Activity(List<CardDetail> reds, List<ActivityThread> threads) {
        this.reds = reds;
        this.threads = threads;
    }

    public List<CardDetail> getReds() {
        return reds;
    }

    public void setReds(List<CardDetail> reds) {
        this.reds = reds;
    }

    public List<ActivityThread> getThreads() {
        return threads;
    }

    public void setThreads(List<ActivityThread> threads) {
        this.threads = threads;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "reds=" + reds +
                ", threads=" + threads +
                '}';
    }
}
