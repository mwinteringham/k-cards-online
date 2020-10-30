package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityThread {

//    "threads": [{
//        "name": "Amy Lee"
//    }, {
//        "name": "Barry White",
//        "subthreads": [{
//            "name": "Stevie"
//        }, {
//            "name": "David"
//        }]
//    }]

    @JsonProperty
    private String name;

    public ActivityThread() {
    }

    public ActivityThread(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ActivityThread{" +
                "name='" + name + '\'' +
                '}';
    }
}
