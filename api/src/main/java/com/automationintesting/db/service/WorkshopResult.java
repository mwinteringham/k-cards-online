package com.automationintesting.db.service;

import com.automationintesting.model.Workshop;
import org.springframework.http.HttpStatus;

public class WorkshopResult {

    private HttpStatus httpStatus;

    private Workshop workshop;

    public WorkshopResult(HttpStatus httpStatus, Workshop workshop) {
        this.httpStatus = httpStatus;
        this.workshop = workshop;
    }

    public WorkshopResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Workshop getCode() {
        return workshop;
    }

}
