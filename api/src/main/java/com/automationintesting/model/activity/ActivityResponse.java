package com.automationintesting.model.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityResponse {

//    {
//        "activity": {
//            "reds": [
//              "Amy Lee",
//              "Barry White",
//              "Jerry Jones"
//            ],
//            "threads": [{
//                "name": "Amy Lee"
//            }, {
//                "name": "Barry White",
//                "subthreads": [{
//                    "name": "Stevie"
//                }, {
//                    "name": "David"
//                }]
//            }]
//        }
//    }

    @JsonProperty
    private Activity activity;

    public ActivityResponse() {
    }

    public ActivityResponse(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "ActivityResponse{" +
                "activity=" + activity.toString() +
                '}';
    }
}
