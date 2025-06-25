package com.github.hippoom.runner.challenges.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.github.hippoom.runner.challenges.domain.model.activity.UserActivity;
import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import com.github.hippoom.runner.challenges.http.support.TestSessionTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static com.github.hippoom.runner.challenges.domain.model.activity.UserActivityTestDataBuilder.aUserActivity;
import static com.github.hippoom.runner.challenges.domain.model.user.UserIdTestDataBuilder.aUserId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RunnerSteps {

    @LocalServerPort
    private int mainPort;

    @LocalManagementPort
    private int managementPort;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private TestSessionTokenService testSessionTokenService;

    private ResponseEntity<String> challengesResponse;
    private ResponseEntity<String> startChallengeResponse;
    private int startedChallengeNumber;
    private String currentSessionToken;
    private UserId currentUserId;

    @Before
    public void setupSessionToken() {
        currentSessionToken = UUID.randomUUID().toString();
        currentUserId = aUserId().build();
        testSessionTokenService.registerSession(currentSessionToken, currentUserId);
    }

    @When("I request to list my challenges")
    public void iRequestToListMyChallenges() {
        String challengesUrl = "http://localhost:" + mainPort + "/api/my/challenges";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Session-Token", currentSessionToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        challengesResponse = restTemplate.exchange(challengesUrl, HttpMethod.GET, entity, String.class);
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

    @When("I select the challenge {int} to start")
    public void iSelectTheChallengeToStart(int challengeNumber) {
        this.startedChallengeNumber = challengeNumber;
        String startChallengeUrl = "http://localhost:" + mainPort + "/api/my/challenges/" + challengeNumber + "/start";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Session-Token", currentSessionToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        startChallengeResponse = restTemplate.exchange(startChallengeUrl, HttpMethod.POST, entity, String.class);
    }

    @Then("the challenge should be marked as started")
    public void theChallengeShouldBeMarkedAsStarted() throws Exception {
        assertEquals(HttpStatus.OK, startChallengeResponse.getStatusCode(),
                "Start challenge endpoint should return HTTP 200");

        JsonNode challengeJson = objectMapper.readTree(startChallengeResponse.getBody());
        
        assertEquals(startedChallengeNumber, challengeJson.get("number").asInt(),
                "Response should contain challenge number " + startedChallengeNumber);
        assertTrue(challengeJson.get("is_started").asBoolean(),
                "Challenge " + startedChallengeNumber + " should be marked as started");
    }

    @When("I upload a running activity")
    public void iUploadARunningActivity() {
        // Create and publish UserActivity event using test data builder
        UserActivity userActivity = aUserActivity().withUserId(currentUserId).build();
        eventPublisher.publishEvent(userActivity);
    }

    @Then("the challenge should be marked as completed")
    public void theChallengeShouldBeMarkedAsCompleted() throws Exception {
        // Get the challenge list to check if the started challenge is completed
        String challengesUrl = "http://localhost:" + mainPort + "/api/my/challenges";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Session-Token", currentSessionToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(challengesUrl, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Challenges endpoint should return HTTP 200");

        JsonNode responseJson = objectMapper.readTree(response.getBody());
        JsonNode challengesArray = responseJson.get("_embedded").get("challenges");
        
        // Find the specific challenge that was started
        JsonNode startedChallenge = null;
        for (JsonNode challenge : challengesArray) {
            if (challenge.get("number").asInt() == startedChallengeNumber) {
                startedChallenge = challenge;
                break;
            }
        }
        
        assertNotNull(startedChallenge, 
                "Challenge " + startedChallengeNumber + " should be found in the response");
        assertTrue(startedChallenge.get("is_completed").asBoolean(),
                "Challenge " + startedChallengeNumber + " should be marked as completed");
    }

    @Then("I should be told that the challenge is unavailable")
    public void iShouldBeToldThatTheChallengeIsUnavailable() {
        assertEquals(HttpStatus.PRECONDITION_FAILED, startChallengeResponse.getStatusCode(),
                "Start challenge endpoint should return HTTP 412 for unavailable challenge");
    }
} 