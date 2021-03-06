Feature: Sending cards

  @auto
  Scenario: Attendee raises a red card for the host
    Given I am in a workshop created by a host
    When I send a "red" card to the host
    Then the host should see a red card has been sent by me

  @auto
  Scenario: Attendee raises a green card for the host
    Given I am in a workshop created by a host
    When I send a "green" card to the host
    Then the host should see a green card has been sent by me

  @auto
  Scenario: Attendee raises a yellow card for the host
    Given I am in a workshop created by a host
    And I sent a green card to the host
    When I send a yellow card to the host
    Then the host should see a yellow card has been attached to the latest green card

  @auto
  Scenario: Attendee attempts to send a card when not joined
    Given a workshop exists that I haven't joined yet
    When I send a card before joining the workshop
    Then I should get an error informing me I can't add a card
