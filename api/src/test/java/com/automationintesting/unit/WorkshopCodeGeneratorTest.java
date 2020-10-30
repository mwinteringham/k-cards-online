package com.automationintesting.unit;

import com.automationintesting.service.WorkshopCodeGenerator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class WorkshopCodeGeneratorTest {

    @Test
    public void generateCodeTest(){
        WorkshopCodeGenerator workshopCodeGenerator = new WorkshopCodeGenerator();
        String workshopCode = workshopCodeGenerator.createCode();

        assertThat(workshopCode, instanceOf(String.class));
        assertThat(workshopCode.length(), equalTo(6));
    }

}
