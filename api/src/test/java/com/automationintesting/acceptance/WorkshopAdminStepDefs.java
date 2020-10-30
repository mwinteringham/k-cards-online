package com.automationintesting.acceptance;

import com.automationintesting.model.Workshop;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkshopAdminStepDefs {

    private Workshop workshopRequest;
    private Workshop workshopResponse;

    @Given("I have picked a name for a workshop")
    public void setAHostName() {
        workshopRequest = new Workshop("LEWT");
    }

    @When("I request to host a workshop")
    public void sendAWorkshopRequest() {
        workshopResponse = given()
                .contentType(ContentType.JSON)
                .body(workshopRequest)
                .post("http://localhost:8080/workshop")
                .as(Workshop.class);
    }

    @Then("a workshop should be created with a code to share with guests")
    public void checkWorkshopHasCode() {
        String workshopCode = workshopResponse.getCode();
        String workshopName = workshopResponse.getName();

        assertThat(workshopCode, instanceOf(String.class));
        assertThat(workshopName, equalTo("LEWT"));
    }

}
