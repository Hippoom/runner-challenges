Feature: Deployment Smoke Test
  As a system administrator
  I want to verify the application is running
  So that I can ensure successful deployment

  Scenario: Application is up
    When I request the health endpoint
    Then I should receive a status of "UP"
