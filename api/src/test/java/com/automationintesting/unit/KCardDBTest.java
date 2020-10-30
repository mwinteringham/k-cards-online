package com.automationintesting.unit;

import com.automationintesting.db.KCardDB;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class KCardDBTest {

    @Test
    public void storeWorkshopCodeInDb() throws SQLException {
        String workshopCode = "abcdef";
        String workshopName = "LEWT";
        KCardDB KCardDb = new KCardDB();

        Boolean storeResult = KCardDb.addCode(workshopCode, workshopName);
        assertThat(storeResult, equalTo(true));
    }

}
