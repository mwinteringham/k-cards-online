package acceptance;

import com.automationintesting.model.RoomRequest;
import com.automationintesting.model.Code;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateRoomStepDefs {

    private RoomRequest roomRequest;
    private Code code;

    @Given("I have set a name")
    public void setAHostName() {
        roomRequest = new RoomRequest("Mark");
    }

    @When("I request to host a room")
    public void sendARoomRequest() {
        code = given()
                .contentType(ContentType.JSON)
                .body(roomRequest)
                .post("http://localhost:8080/room")
                .as(Code.class);
    }

    @Then("a room should be created with a code to share with guests")
    public void checkRoomHasCode() {
        String roomCode = code.getCode();

        assertThat(roomCode, instanceOf(String.class));
    }

}
