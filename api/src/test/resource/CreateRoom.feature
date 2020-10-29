Feature: Creating a Room

  Scenario: Host creates a room
    Given I have set a name
    When I request to host a room
    Then a room should be created with a code to share with guests
