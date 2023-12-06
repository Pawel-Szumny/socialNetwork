Feature: Display list of users

  Scenario: List of users is displayed
    When user requests the list of users
    Then the list of users is successfully displayed
