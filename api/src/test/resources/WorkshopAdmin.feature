Feature: Workshop administration

  @auto
  Scenario: Host creates a workshop
    Given I have picked a name for a workshop
    When I request to host a workshop
    Then a workshop should be created with a code to share with guests

  @wip
  Scenario: Host is alerted of new attendee
    Given I have created a new workshop
    When an attendee joins my workshop
    Then I should be able to see that the attendee has joined
