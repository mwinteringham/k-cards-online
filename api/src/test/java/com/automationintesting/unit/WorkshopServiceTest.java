package com.automationintesting.unit;

import com.automationintesting.db.KCardDB;
import com.automationintesting.db.service.WorkshopResult;
import com.automationintesting.model.Workshop;
import com.automationintesting.service.WorkshopService;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class WorkshopServiceTest {

    @Mock
    private KCardDB kCardDB;

    @Autowired
    @InjectMocks
    private WorkshopService workshopService;

    @Before
    public void initialiseMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createWorkshopTest() throws SQLException {
        when(kCardDB.addCode(anyString(), eq("LEWT"))).thenReturn(true);

        WorkshopResult workshopResult = workshopService.createWorkshop("LEWT");

        assertThat(workshopResult.getHttpStatus(), equalTo(HttpStatus.CREATED));
        assertThat(workshopResult.getCode(), instanceOf(Workshop.class));
    }

}
