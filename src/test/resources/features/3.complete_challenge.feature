Feature: Complete a Challenge
  As a runner
  I want to complete challenges by uploading my running activities
  So that I can track my progress and achievements

  Scenario: Complete a challenge without criteria
    Given I select the challenge 1 to start
    And the challenge does not require any completion criteria
    And the challenge should be marked as started
    When I upload a running activity
    Then the challenge should be marked as completed

  Scenario: Complete a challenge when activity meets distance criteria
    Given I select the challenge 4 to start
    And the challenge requires a minimum distance of 5.0 km
    And the challenge should be marked as started
    When I upload a running activity with required distance
    Then the challenge should be marked as completed

  Scenario: Complete a challenge when activity meets minimum pace criteria
    Given I select the challenge 5 to start
    And the challenge requires a minimum pace of 6.0 minutes per km
    And the challenge should be marked as started
    When I upload a running activity with required pace
    Then the challenge should be marked as completed


