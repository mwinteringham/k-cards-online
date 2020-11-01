package com.automationintesting.acceptance;

import com.automationintesting.acceptance.requests.WorkshopRequests;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.model.activity.ActivityResponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class SendingCardsStepDefs {

    private WorkshopRequests workshopRequests = new WorkshopRequests();
    private Workshop workshopResponse;
    private Attendee attendee;
    private Response errorResponse;

    @Before
    public void createWorkshop(){
        Workshop defaultWorkshopRequestPayload = new Workshop("CEWT");
        workshopResponse = workshopRequests.createWorkshop(defaultWorkshopRequestPayload);
    }

    @Given("I am in a workshop created by a host")
    public void joinAWorkshop() {
        Attendee attendeeToCreate = new Attendee("Amy Lee");
        attendee = workshopRequests.joinWorkshopAsAttendee(attendeeToCreate, workshopResponse.getCode()).as(Attendee.class);
    }

    @When("I send a {string} card to the host")
    public void sendACard(String cardType) {
        Card card = new Card(attendee.getCode(), cardType);

        Response response = workshopRequests.sendCard(card, workshopResponse.getCode());

        assertThat(response.statusCode(), equalTo(201));
    }

    @Then("the host should see a red card has been sent by me")
    public void hostChecksRedCard() {
        ActivityResponse activity = workshopRequests.getActivity(workshopResponse.getCode());

        assertThat(activity.getActivity().getReds().get(0).getName(), equalTo("Amy Lee"));
    }

    @Then("the host should see a green card has been sent by me")
    public void hostChecksGreenCard() {
        ActivityResponse activity = workshopRequests.getActivity(workshopResponse.getCode());

        String threadName = activity.getActivity().getThreads().get(0).getCardDetail().getName();

        assertThat(threadName, equalTo("Amy Lee"));
    }

    @Given("I sent a green card to the host")
    public void sendAGreenCard() {
        Card card = new Card(attendee.getCode(), "green");

        workshopRequests.sendCard(card, workshopResponse.getCode());
    }

    @When("I send a yellow card to the host")
    public void sendAYellowCard() {
        Card card = new Card(attendee.getCode(), "yellow");

        workshopRequests.sendCard(card, workshopResponse.getCode());
    }

    @Then("the host should see a yellow card has been attached to the latest green card")
    public void hostChecksYellowCard() {
        ActivityResponse activity = workshopRequests.getActivity(workshopResponse.getCode());

        String threadName = activity.getActivity().getThreads().get(0).getSubThread().get(0).getName();

        assertThat(threadName, equalTo("Amy Lee"));
    }

    @Given("a workshop exists that I haven't joined yet")
    public void createWorkshopToNotJoin() {
        // Nothing to do here as before hook handles this work for us
    }


    @When("I send a card before joining the workshop")
    public void sendACardBeforeJoining() {
        Card card = new Card("abcdef", "red");
        errorResponse = workshopRequests.sendCard(card, workshopResponse.getCode());
    }

    @Then("I should get an error informing me I can't add a card")
    public void checkForError() {
        assertThat(errorResponse.statusCode(), equalTo(404));
    }
}
