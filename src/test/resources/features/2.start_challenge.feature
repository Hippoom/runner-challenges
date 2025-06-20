@to-be-implemented
Feature: Start a Challenge
  As a runner
  I want to start a challenge
  So that I can complete it

  Scenario: Start a challenge
    When I select the challenge "1" to start
    Then the challenge should be marked as started

  Scenario: Cannot start a challenge given it is locked
    When I select the challenge "2" to start
    Then I should be told that the challenge is unavailable

  Scenario: Cannot start a challenge given its prerequisites not completed
    When I select the challenge "3" to start
    Then I should be told that the challenge is unavailable


