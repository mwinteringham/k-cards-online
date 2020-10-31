Feature: Attendee connection

  @auto
  Scenario: Attendee joins a workshop
    Given a workshop has been created
    When I ask to join the workshop
    Then I'm informed that I've joined

  @auto
  Scenario: Attendee leaves a workshop
    Given I've joined a workshop
    And I've sent cards
    When I ask to leave the workshop
    Then I am informed that I've left
    And all my cards are removed

  @auto
  Scenario: Attendee joins a workshop that doesn't exist
    Given a workshop hasn't been created
    When I ask to join the workshop
    Then I'm informed that the workshop doesn't exist