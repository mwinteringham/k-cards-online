Feature: Sending cards

  @auto
  Scenario: Attendee raises a red card for the host
    Given I am in a workshop created by a host
    When I send a red card to the host
    Then the host should see a red card has been sent by me

  @wip
  Scenario: Attendee raises a green card for the host
    Given I am in a workshop created by a host
    When I send a green card to the host
    Then the host should see a green card has been sent by me

  @wip
  Scenario: Attendee raises a yellow card for the host
    Given I am in a workshop created by a host
    And a thread is currently active
    When I send a yellow card to the host
    Then the host should see a yellow card has been attached to the thread
    