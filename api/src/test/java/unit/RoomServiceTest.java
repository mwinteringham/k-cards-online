package unit;

import com.automationintesting.db.KCardDB;
import com.automationintesting.db.service.RoomResult;
import com.automationintesting.model.WorkshopRoom;
import com.automationintesting.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    @Mock
    private KCardDB kCardDB;

    @Autowired
    @InjectMocks
    private RoomService roomService;

    @Before
    public void initialiseMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createRoomTest() throws SQLException {
        when(kCardDB.addCode(anyString(), "LEWT")).thenReturn(true);

        RoomResult roomResult = roomService.createRoom("LEWT");

        assertThat(roomResult.getHttpStatus(), equalTo(HttpStatus.CREATED));
        assertThat(roomResult.getCode(), instanceOf(WorkshopRoom.class));
    }

}
