package unit;

import com.automationintesting.db.KCardDB;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RoomDbTest {


    @Test
    public void storeRoomCodeInDb() throws SQLException {
        String roomCode = "abcdef";
        KCardDB KCardDb = new KCardDB();

        Boolean storeResult = KCardDb.addCode(roomCode);
        assertThat(storeResult, equalTo(true));
    }

}
