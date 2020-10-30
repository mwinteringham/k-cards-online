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
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.approvaltests.Approvals;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class SendingCardsStepDefs {

    private WorkshopRequests workshopRequests = new WorkshopRequests();
    private Workshop workshopResponse;

    @Before
    public void createWorkshop(){
        Workshop defaultWorkshopRequestPayload = new Workshop("CEWT");
        workshopResponse = workshopRequests.createWorkshop(defaultWorkshopRequestPayload);
    }

    @Given("I am in a workshop created by a host")
    public void joinAWorkshop() {
        Attendee attendee = new Attendee("Amy Lee");
        workshopRequests.joinWorkshopAsAttendee(attendee, workshopResponse.getCode());
    }

    @When("I send a red card to the the host")
    public void sendARedCard() {
        Card card = new Card("Amy Lee", "red");

        Response response = given()
                                .contentType(ContentType.JSON)
                                .body(card)
                                .post("http://localhost:8080/workshop/" + workshopResponse.getCode() + "/card");

        assertThat(response.statusCode(), equalTo(201));
    }

    @Then("the host should see a red card has been sent by me")
    public void hostChecksCards() {
        ActivityResponse activity = given()
                                .get("http://localhost:8080/workshop/" + workshopResponse.getCode() + "/activity")
                                .as(ActivityResponse.class);

        assertThat(activity.getActivity().getReds(), containsInAnyOrder("Amy Lee"));
    }
}
