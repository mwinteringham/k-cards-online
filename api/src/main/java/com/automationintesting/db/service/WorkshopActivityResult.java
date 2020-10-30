package com.automationintesting.db.service;

import com.automationintesting.model.activity.ActivityResponse;
import org.springframework.http.HttpStatus;

public class WorkshopActivityResult {

    private HttpStatus httpStatus;

    private ActivityResponse activity;

    public WorkshopActivityResult(HttpStatus httpStatus, ActivityResponse activity) {
        this.httpStatus = httpStatus;
        this.activity = activity;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ActivityResponse getActivity() {
        return activity;
    }
}
