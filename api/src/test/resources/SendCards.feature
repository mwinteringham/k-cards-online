Feature: Sending cards

  @auto
  Scenario: Attendee raises a card for the host
    Given I am in a workshop created by a host
    When I send a red card to the the host
    Then the host should see a red card has been sent by me