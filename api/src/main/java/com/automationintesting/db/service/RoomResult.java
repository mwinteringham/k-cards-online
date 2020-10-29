package com.automationintesting.db.service;

import com.automationintesting.model.Code;
import org.springframework.http.HttpStatus;

public class RoomResult {

    private HttpStatus httpStatus;

    private Code code;

    public RoomResult(HttpStatus httpStatus, Code code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public RoomResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Code getCode() {
        return code;
    }

}
