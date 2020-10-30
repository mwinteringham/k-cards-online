Feature: Room administration

  Scenario: Host creates a room
    Given I am hosting a workshop
    When I request to host a room
    Then a room should be created with a code to share with guests

  Scenario: Host is alerted of new attendee
    Given I have created a new room
    When an attendee joins my room
    Then I should be able to see that the attendee has joined
