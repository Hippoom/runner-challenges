@to-be-implemented
Feature: List My Challenges
  As a runner
  I want to see a list of my challenges
  So that I can track my progress and know which challenges are available to me

  Scenario: View all challenges
    When I request to list my challenges
    Then I should see all challenges sorted by number
