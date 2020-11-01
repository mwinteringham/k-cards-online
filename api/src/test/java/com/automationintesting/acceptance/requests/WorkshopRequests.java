package com.automationintesting.acceptance.requests;

import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.model.activity.ActivityResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class WorkshopRequests {

    public Workshop createWorkshop(Workshop workshopRequestPayload) {
        return given()
                .contentType(ContentType.JSON)
                .body(workshopRequestPayload)
                .post("http://localhost:8080/workshop")
                .as(Workshop.class);
    }

    public Response joinWorkshopAsAttendee(Attendee attendee, String workshopCode){
        return given()
            .contentType(ContentType.JSON)
            .body(attendee)
            .post("http://localhost:8080/workshop/" + workshopCode + "/join");
    }

    public Response sendCard(Card card, String workshopCode){
        return given()
                .contentType(ContentType.JSON)
                .body(card)
                .post("http://localhost:8080/workshop/" + workshopCode + "/card");
    }

    public ActivityResponse getActivity(String workshopCode){
        return given()
                .get("http://localhost:8080/workshop/" + workshopCode + "/activity")
                .as(ActivityResponse.class);
    }

    public void deleteCard(String workshopCode, List<String> cardIds) {
        given()
            .contentType(ContentType.JSON)
            .body(cardIds)
            .delete("http://localhost:8080/workshop/" + workshopCode + "/card");
    }

    public AttendeeList getAttendeeList(String workshopCode){
        return given()
                .get("http://localhost:8080/workshop/" + workshopCode + "/attendees")
                .as(AttendeeList.class);
    }
}
