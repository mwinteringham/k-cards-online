package com.automationintesting.acceptance;

import com.automationintesting.acceptance.requests.WorkshopRequests;
import com.automationintesting.model.Attendee;
import com.automationintesting.model.AttendeeList;
import com.automationintesting.model.Card;
import com.automationintesting.model.Workshop;
import com.automationintesting.model.activity.ActivityResponse;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkshopAdminStepDefs {

    private WorkshopRequests workshopRequests = new WorkshopRequests();

    private Workshop workshopRequestPayload;
    private Workshop workshopResponse;

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

    @And("a red card has been sent")
    public void sendARedCard() {
        Attendee attendee = new Attendee("Gene Hamm");
        Attendee createdAttendee = workshopRequests.joinWorkshopAsAttendee(attendee, workshopResponse.getCode()).as(Attendee.class);

        Card redCard = new Card(createdAttendee.getCode(), "red");
        workshopRequests.sendCard(redCard, workshopResponse.getCode());
    }

    @When("I delete the red card")
    public void deleteTheRedCard() {
        ActivityResponse activityResponse = workshopRequests.getActivity(workshopResponse.getCode());
        String cardId = activityResponse.getActivity().getReds().get(0).getCode();

        List<String> cardsToDelete = new ArrayList<String>(){{
           this.add(cardId);
        }};

        workshopRequests.deleteCard(workshopResponse.getCode(), cardsToDelete);
    }

    @Then("the red card should be removed from the activity")
    public void checkRedCardHasBeenRemoved() {
        ActivityResponse activityResponse = workshopRequests.getActivity(workshopResponse.getCode());

        assertThat(activityResponse.getActivity().getReds().size(), equalTo(0));
    }

    @When("I delete the green card")
    public void deleteGreenCard() {
        ActivityResponse activityResponse = workshopRequests.getActivity(workshopResponse.getCode());
        String cardId1 = activityResponse.getActivity().getThreads().get(0).getCardDetail().getCode();
        String cardId2 = activityResponse.getActivity().getThreads().get(0).getSubThread().get(0).getCode();

        List<String> cardIds = new ArrayList<>(){{
            this.add(cardId1);
            this.add(cardId2);
        }};

        workshopRequests.deleteCard(workshopResponse.getCode(), cardIds);
    }

    @Then("the green card and related yellow cards should be removed from the activity")
    public void checkGreenCardHasBeenRemoved() {
        ActivityResponse activityResponse = workshopRequests.getActivity(workshopResponse.getCode());

        assertThat(activityResponse.getActivity().getThreads().size(), equalTo(0));
    }

    @And("a green card with a yellow card has been sent")
    public void aGreenAndYellowCardHasBeenSent() {
        Attendee attendee = new Attendee("Gene Eggs");
        Attendee createdAttendee = workshopRequests.joinWorkshopAsAttendee(attendee, workshopResponse.getCode()).as(Attendee.class);

        Card greenCard = new Card(createdAttendee.getCode(), "green");
        Card yellowCard = new Card(createdAttendee.getCode(), "yellow");
        workshopRequests.sendCard(greenCard, workshopResponse.getCode());
        workshopRequests.sendCard(yellowCard, workshopResponse.getCode());
    }

    @When("I delete the yellow card")
    public void deleteYellowCard() {
        ActivityResponse activityResponse = workshopRequests.getActivity(workshopResponse.getCode());
        String cardId = activityResponse.getActivity().getThreads().get(0).getSubThread().get(0).getCode();

        List<String> cardIds = new ArrayList<>(){{
            this.add(cardId);
        }};

        workshopRequests.deleteCard(workshopResponse.getCode(), cardIds);
    }

    @Then("the yellow card should be removed from the activity")
    public void checkYellowCardHasBeenDeleted() {
        ActivityResponse activityResponse = workshopRequests.getActivity(workshopResponse.getCode());

        assertThat(activityResponse.getActivity().getThreads().get(0).getSubThread().size(), equalTo(0));
    }
}
