package com.automationintesting.unit;

import com.automationintesting.service.CodeGenerator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class CodeGeneratorTest {

    @Test
    public void generateCodeTest(){
        CodeGenerator codeGenerator = new CodeGenerator();
        String workshopCode = codeGenerator.createCode();

        assertThat(workshopCode, instanceOf(String.class));
        assertThat(workshopCode.length(), equalTo(6));
    }

}
