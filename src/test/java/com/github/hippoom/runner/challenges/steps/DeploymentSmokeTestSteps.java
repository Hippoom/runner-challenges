package com.github.hippoom.runner.challenges.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeploymentSmokeTestSteps {

    @LocalServerPort
    private int mainPort;

    @LocalManagementPort
    private int managementPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> healthResponse;

    @When("I request the health endpoint")
    public void iRequestTheHealthEndpoint() {
        String healthUrl = "http://localhost:" + managementPort + "/actuator/health";
        healthResponse = restTemplate.getForEntity(healthUrl, String.class);
    }

    @Then("I should receive a status of \"UP\"")
    public void iShouldReceiveAStatusOfUP() {
        assertEquals(HttpStatus.OK, healthResponse.getStatusCode(), "Health endpoint should return HTTP 200");
        assertTrue(healthResponse.getBody().contains("\"status\":\"UP\""), "Response should contain status UP");
    }
}
