Feature: Workshop administration

  @auto
  Scenario: Host creates a workshop
    Given I have picked a name for a workshop
    When I request to host a workshop
    Then a workshop should be created with a code to share with guests

  @auto
  Scenario: Host is alerted of new attendee
    Given I have created a new workshop
    When an attendee joins my workshop
    Then I should be able to see that the attendee has joined

  @wip
  Scenario: Host sets a thread as active
    Given I have created a new workshop
    And a green card has been sent
    When I select the thread to be the current thread
    Then all yellow cards are added to that thread

  @wip
  Scenario: Host deletes a red card
    Given I have created a new workshop
    And a red card has been sent
    When I delete the red card
    Then the red card should be removed from the activity

  @wip
  Scenario: Host deletes a green card
    Given I have created a new workshop
    And a green card with a yellow card has been sent
    When I delete the green card
    Then the green card and related yellow cards should be removed from the activity

  @wip
  Scenario: Host deletes a yellow card
    Given I have created a new workshop
    And a green card with a yellow card has been sent
    When I delete the yellow card
    Then the yellow card should be removed from the activity

  @wip
  Scenario: Host closes a workshop
    Given I have created a new workshop
    And a series of cards have been added to the workshop
    When I delete the workshop
    Then the workshop should be removed as well as all related cards
