package com.automationintesting.acceptance.requests;

import com.automationintesting.model.Attendee;
import com.automationintesting.model.Workshop;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class WorkshopRequests {

    public Workshop createWorkshop(Workshop workshopRequestPayload) {
        return given()
                .contentType(ContentType.JSON)
                .body(workshopRequestPayload)
                .post("http://localhost:8080/workshop")
                .as(Workshop.class);
    }

    public void joinWorkshopAsAttendee(Attendee attendee, String workshopCode){
        given()
            .contentType(ContentType.JSON)
            .body(attendee)
            .post("http://localhost:8080/workshop/" + workshopCode + "/join");
    }
}