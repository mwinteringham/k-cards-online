Feature: Sending cards

  @wip
  Scenario: Attendee joins a workshop
    Given a workshop has been created by a host
    When I request to join the workshop with a valid code and my name
    Then I should receive confirmation that I have joined the workshop

  @wip
  Scenario: Attendee raises a card for the host
    Given I am in a workshop created by a host
    When I send a red card to the the host
    Then the host should see a red card has been sent by me