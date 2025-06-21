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

public class RunnerSteps {

    @LocalServerPort
    private int mainPort;

    @LocalManagementPort
    private int managementPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> challengesResponse;

    @When("I request to list my challenges")
    public void iRequestToListMyChallenges() {
        String challengesUrl = "http://localhost:" + mainPort + "/api/my/challenges";
        challengesResponse = restTemplate.getForEntity(challengesUrl, String.class);
    }

        @Then("I should see all challenges sorted by number")
    public void iShouldSeeAllChallengesSortedByNumber() {
        assertEquals(HttpStatus.OK, challengesResponse.getStatusCode(), 
                "Challenges endpoint should return HTTP 200");
        
        String responseBody = challengesResponse.getBody();
        assertTrue(responseBody.contains("\"_embedded\""), "Response should contain _embedded");
        assertTrue(responseBody.contains("\"challenges\""), 
                "Response should contain challenges array per OpenAPI spec");
        
        // Check that challenges are in ascending order
        int pos1 = responseBody.indexOf("\"number\":1");
        int pos2 = responseBody.indexOf("\"number\":2");
        int pos3 = responseBody.indexOf("\"number\":3");
        int pos4 = responseBody.indexOf("\"number\":4");
        int pos5 = responseBody.indexOf("\"number\":5");
        
        assertTrue(pos1 > 0 && pos2 > pos1 && pos3 > pos2 && pos4 > pos3 && pos5 > pos4, 
                   "Challenges should be sorted in ascending order by number");
    }
}
