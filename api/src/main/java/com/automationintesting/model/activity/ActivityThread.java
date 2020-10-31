package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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
    @JsonProperty
    private List<String> subthread = new ArrayList<>();

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

    public List<String> getSubThread() {
        return subthread;
    }

    public void addToSubThread(String name){
        subthread.add(name);
    }

    @Override
    public String toString() {
        return "ActivityThread{" +
                "name='" + name + '\'' +
                ", subthreads=" + subthread +
                '}';
    }
}
