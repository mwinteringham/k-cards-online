package com.automationintesting.acceptance;

import com.automationintesting.acceptance.requests.WorkshopRequests;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Workshop;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkshopAdminStepDefs {

    private WorkshopRequests workshopRequests = new WorkshopRequests();

    private Workshop workshopRequestPayload;
    private Workshop workshopResponse;
    private Response attendeeJoinedResponse;

    @Given("I have picked a name for a workshop")
    public void setAHostName() {
        workshopRequestPayload = new Workshop("LEWT");
    }

    @When("I request to host a workshop")
    public void sendAWorkshopRequest() {
        workshopResponse = workshopRequests.createWorkshop(workshopRequestPayload);
    }

    @Then("a workshop should be created with a code to share with guests")
    public void checkWorkshopHasCode() {
        String workshopCode = workshopResponse.getCode();
        String workshopName = workshopResponse.getName();

        assertThat(workshopCode, instanceOf(String.class));
        assertThat(workshopName, equalTo("LEWT"));
    }

    @Given("I have created a new workshop")
    public void createANewWorkshop() {
        Workshop defaultWorkshopRequestPayload = new Workshop("DEWT");
        workshopResponse = workshopRequests.createWorkshop(defaultWorkshopRequestPayload);
    }

    @When("an attendee joins my workshop")
    public void joinWorkshopAsAttendee() {
        Attendee attendee = new Attendee("James Dean");

        workshopRequests.joinWorkshopAsAttendee(attendee, workshopResponse.getCode());
    }

    @Then("I should be able to see that the attendee has joined")
    public void pollCurrentAttendees() {
        AttendeeList attendeeList = given()
                                        .get("http://localhost:8080/workshop/" + workshopResponse.getCode() + "/attendees")
                                        .as(AttendeeList.class);

        String attendeeName = attendeeList.getAttendees().get(0).getName();

        assertThat(attendeeName, equalTo("James Dean"));
    }

}
