package com.automationintesting.db.service;

import com.automationintesting.model.WorkshopRoom;
import org.springframework.http.HttpStatus;

public class RoomResult {

    private HttpStatus httpStatus;

    private WorkshopRoom workshopRoom;

    public RoomResult(HttpStatus httpStatus, WorkshopRoom workshopRoom) {
        this.httpStatus = httpStatus;
        this.workshopRoom = workshopRoom;
    }

    public RoomResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public WorkshopRoom getCode() {
        return workshopRoom;
    }

}
