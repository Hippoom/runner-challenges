package com.github.hippoom.runner.challenges.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
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

    // Constants
    private static final String CHALLENGES_ENDPOINT = "/api/my/challenges";
    private static final String SESSION_TOKEN_HEADER = "X-Session-Token";
    private static final double ASSERTION_DELTA = 0.01;

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

    /**
     * Creates HTTP headers with authentication token for API requests.
     */
    private HttpHeaders createAuthenticatedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(SESSION_TOKEN_HEADER, currentSessionToken);
        return headers;
    }

    /**
     * Makes HTTP GET request to challenges endpoint and returns the response.
     */
    private ResponseEntity<String> getChallengesResponse() {
        String challengesUrl = "http://localhost:" + mainPort + CHALLENGES_ENDPOINT;
        HttpEntity<String> entity = new HttpEntity<>(createAuthenticatedHeaders());
        return restTemplate.exchange(challengesUrl, HttpMethod.GET, entity, String.class);
    }

    /**
     * Finds a specific challenge by number in the JSON response.
     * @param responseJson The parsed JSON response containing challenges
     * @param challengeNumber The challenge number to find
     * @return The JsonNode representing the challenge, or null if not found
     */
    private JsonNode findChallengeInResponse(JsonNode responseJson, int challengeNumber) throws Exception {
        JsonNode challengesArray = responseJson.get("_embedded").get("challenges");
        
        for (JsonNode challenge : challengesArray) {
            if (challenge.get("number").asInt() == challengeNumber) {
                return challenge;
            }
        }
        return null;
    }

    @When("I request to list my challenges")
    public void iRequestToListMyChallenges() {
        challengesResponse = getChallengesResponse();
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
        String startChallengeUrl = "http://localhost:" + mainPort + CHALLENGES_ENDPOINT + "/" + challengeNumber + "/start";
        HttpEntity<String> entity = new HttpEntity<>(createAuthenticatedHeaders());
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
    
    @When("I upload a running activity with required distance")
    public void iUploadARunningActivityWithRequiredDistance() {
        // Create activity with 5.5km distance (meets challenge 4's 5.0km requirement)
        UserActivity userActivity = aUserActivity()
                .withUserId(currentUserId)
                .withDistance(5.5)
                .withDuration(1800) // 30 minutes
                .build();
        eventPublisher.publishEvent(userActivity);
    }
    
    @When("I upload a running activity with required pace")
    public void iUploadARunningActivityWithRequiredPace() {
        // Create activity with 5.5 min/km pace and 5km distance (meets challenge 5's 6.0 min/km requirement)
        UserActivity userActivity = aUserActivity()
                .withUserId(currentUserId)
                .withPace(5.5, 5.0) // 5.5 min/km pace, 5km distance
                .build();
        eventPublisher.publishEvent(userActivity);
    }

    @Then("the challenge should be marked as completed")
    public void theChallengeShouldBeMarkedAsCompleted() throws Exception {
        ResponseEntity<String> response = getChallengesResponse();

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Challenges endpoint should return HTTP 200");

        JsonNode responseJson = objectMapper.readTree(response.getBody());
        JsonNode startedChallenge = findChallengeInResponse(responseJson, startedChallengeNumber);
        
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

    @Given("the challenge requires a minimum distance of {double} km")
    public void theChallengeRequiresAMinimumDistanceOfKm(double expectedDistance) throws Exception {
        ResponseEntity<String> response = getChallengesResponse();

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Challenges endpoint should return HTTP 200");

        JsonNode responseJson = objectMapper.readTree(response.getBody());
        JsonNode targetChallenge = findChallengeInResponse(responseJson, startedChallengeNumber);
        
        assertNotNull(targetChallenge, 
                "Challenge " + startedChallengeNumber + " should be found in the response");
        
        JsonNode minimumDistanceNode = targetChallenge.get("minimum_distance");
        assertNotNull(minimumDistanceNode, 
                "Challenge " + startedChallengeNumber + " should have minimum_distance field");
        
        assertEquals(expectedDistance, minimumDistanceNode.asDouble(), ASSERTION_DELTA,
                "Challenge " + startedChallengeNumber + " minimum distance should be " + expectedDistance + " km");
    }

    @Given("the challenge requires a minimum pace of {double} minutes per km")
    public void theChallengeRequiresAMinimumPaceOfMinutesPerKm(double expectedPace) throws Exception {
        ResponseEntity<String> response = getChallengesResponse();

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Challenges endpoint should return HTTP 200");

        JsonNode responseJson = objectMapper.readTree(response.getBody());
        JsonNode targetChallenge = findChallengeInResponse(responseJson, startedChallengeNumber);
        
        assertNotNull(targetChallenge, 
                "Challenge " + startedChallengeNumber + " should be found in the response");
        
        JsonNode minimumPaceNode = targetChallenge.get("minimum_pace");
        assertNotNull(minimumPaceNode, 
                "Challenge " + startedChallengeNumber + " should have minimum_pace field");
        
        assertEquals(expectedPace, minimumPaceNode.asDouble(), ASSERTION_DELTA,
                "Challenge " + startedChallengeNumber + " minimum pace should be " + expectedPace + " minutes per km");
    }

    @Given("the challenge does not require any completion criteria")
    public void theChallengeDoesNotRequireAnyCompletionCriteria() throws Exception {
        ResponseEntity<String> response = getChallengesResponse();

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Challenges endpoint should return HTTP 200");

        JsonNode responseJson = objectMapper.readTree(response.getBody());
        JsonNode targetChallenge = findChallengeInResponse(responseJson, startedChallengeNumber);
        
        assertNotNull(targetChallenge, 
                "Challenge " + startedChallengeNumber + " should be found in the response");
        
        JsonNode minimumDistanceNode = targetChallenge.get("minimum_distance");
        JsonNode minimumPaceNode = targetChallenge.get("minimum_pace");
        
        assertTrue(minimumDistanceNode == null || minimumDistanceNode.isNull(),
                "Challenge " + startedChallengeNumber + " should not have minimum distance requirement");
        assertTrue(minimumPaceNode == null || minimumPaceNode.isNull(),
                "Challenge " + startedChallengeNumber + " should not have minimum pace requirement");
    }
} 