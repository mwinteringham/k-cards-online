package com.automationintesting.acceptance;

import com.automationintesting.model.WorkshopRoom;
import com.automationintesting.model.RoomRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateRoomStepDefs {

    private RoomRequest roomRequest;
    private WorkshopRoom workshopRoom;

    @Given("I am hosting a workshop")
    public void setAHostName() {
        roomRequest = new RoomRequest("LEWT");
    }

    @When("I request to host a room")
    public void sendARoomRequest() {
        workshopRoom = given()
                .contentType(ContentType.JSON)
                .body(roomRequest)
                .post("http://localhost:8080/room")
                .as(WorkshopRoom.class);
    }

    @Then("a room should be created with a code to share with guests")
    public void checkRoomHasCode() {
        String roomCode = workshopRoom.getCode();
        String roomName = workshopRoom.getWorkshopName();

        assertThat(roomCode, instanceOf(String.class));
        assertThat(roomName, equalTo("LEWT"));
    }

}
