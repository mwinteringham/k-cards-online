package com.automationintesting.acceptance;

import com.automationintesting.acceptance.requests.WorkshopRequests;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.model.activity.Activity;
import com.automationintesting.model.activity.ActivityResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AttendeeConnectionStepDefs {

    private Workshop workshopResponse;
    private Response joinResponse;
    private Response leaveResponse;
    private Attendee attendee;

    private WorkshopRequests workshopRequests = new WorkshopRequests();

    @Given("a workshop has been created")
    public void createWorkshop() {
        workshopResponse = workshopRequests.createWorkshop(new Workshop("BREWT"));
    }

    @When("I ask to join the workshop")
    public void joinWorkshop() {
        Attendee attendee = new Attendee("Mark");

        joinResponse = workshopRequests.joinWorkshopAsAttendee(attendee, workshopResponse.getCode());
    }

    @Then("I'm informed that I've joined")
    public void confirmJoining() {
        assertThat(joinResponse.statusCode(), equalTo(201));
        assertThat(joinResponse.as(Attendee.class).getCode(), instanceOf(String.class));
    }

    @Given("a workshop hasn't been created")
    public void createAFakeWorkshop() {
        workshopResponse = new Workshop("madeup");
    }

    @Then("I'm informed that the workshop doesn't exist")
    public void confirmRejection() {
        assertThat(joinResponse.statusCode(), equalTo(404));
    }

    @Given("I've joined a workshop")
    public void joinAWorkshop() {
        workshopResponse = workshopRequests.createWorkshop(new Workshop("BREWT"));

        attendee = new Attendee("Greg White");
        workshopRequests.joinWorkshopAsAttendee(attendee, workshopResponse.getCode());
    }

    @And("I've sent cards")
    public void iVeSentCards() {
        List<Card> cards = new ArrayList<>(){{
            this.add(new Card(attendee.getName(), attendee.getCode(), "red"));
            this.add(new Card(attendee.getName(), attendee.getCode(), "green"));
            this.add(new Card(attendee.getName(), attendee.getCode(), "yellow"));
        }};

        for(Card card : cards){
            workshopRequests.sendCard(card, workshopResponse.getCode());
        }
    }

    @When("I ask to leave the workshop")
    public void leaveWorkshop() {
        leaveResponse = given()
                            .contentType(ContentType.JSON)
                            .body(attendee)
                            .delete("http://localhost:8080/workshop/" + workshopResponse.getCode() + "/leave");
    }

    @Then("I am informed that I've left")
    public void confirmIHaveLeftWorkshop() {
        assertThat(leaveResponse.statusCode(), equalTo(202));
    }

    @And("all my cards are removed")
    public void confirmCardsAreRemoved() {
        ActivityResponse activity = workshopRequests.getActivity(workshopResponse.getCode());

        assertThat(activity.getActivity().getReds().size(), equalTo(0));
        assertThat(activity.getActivity().getThreads().size(), equalTo(0));
    }
}
