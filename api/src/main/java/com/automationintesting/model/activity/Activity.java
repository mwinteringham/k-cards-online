package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Activity {

//    "activity": {
//        "reds": [
//          "Amy Lee",
//          "Barry White",
//          "Jerry Jones"
//        ],
//        "threads": [{
//            "name": "Amy Lee"
//        }, {
//            "name": "Barry White",
//            "subthreads": [{
//                "name": "Stevie"
//            }, {
//                "name": "David"
//            }]
//        }]
//    }

    @JsonProperty
    private List<String> reds;
    @JsonProperty
    private List<ActivityThread> threads;

    public Activity() {
    }

    public Activity(List<String> reds, List<ActivityThread> threads) {
        this.reds = reds;
        this.threads = threads;
    }

    public List<String> getReds() {
        return reds;
    }

    public void setReds(List<String> reds) {
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
