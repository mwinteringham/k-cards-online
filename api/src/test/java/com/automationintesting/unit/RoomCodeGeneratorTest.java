package com.automationintesting.unit;

import com.automationintesting.service.RoomCodeGenerator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class RoomCodeGeneratorTest {

    @Test
    public void generateCodeTest(){
        RoomCodeGenerator roomCodeGenerator = new RoomCodeGenerator();
        String roomCode = roomCodeGenerator.createCode();

        assertThat(roomCode, instanceOf(String.class));
        assertThat(roomCode.length(), equalTo(6));
    }

}
