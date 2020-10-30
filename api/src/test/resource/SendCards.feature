Feature: Sending cards

  Scenario: Attendee joins a room
    Given a room has been created by a host
    When I request to join the room with a valid code and my name
    Then I should receive confirmation that I have joined the room

  Scenario: Attendee raises a card for the host
    Given I am in a room created by a host
    When I send a red card to the the host
    Then the host should see a red card has been sent by me