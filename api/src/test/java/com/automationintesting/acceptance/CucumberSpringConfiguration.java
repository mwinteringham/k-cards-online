package com.automationintesting.acceptance;

import com.automationintesting.api.KCardApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = KCardApplication.class)
public class CucumberSpringConfiguration {
}
