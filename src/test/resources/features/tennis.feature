Feature: Simulate tennis match

  Scenario: Create a match
    Given the following players
      | id | name          |
      | 1  | first player  |
      | 2  | second player |
    When create a match
    Then match status is "Match started"
    And both players have 0 set score
    And last set has 0 score for both players
    And last game has "0" score for both players

  Scenario: score a point
    Given the following players
      | id | name          |
      | 1  | first player  |
      | 2  | second player |
    And the following match
      | id | comment       | first player id | first player's name | second player id | second's player name | first player sets | second player sets |
      | 1  | Match started | 1               | first player        | 1                | second player        | 1                 | 1                  |
    And last game score
      | first player | second player |
      | 15           | 40            |
    When first player scores a point
    Then last game score becomes
      | first player | second player |
      | 30           | 40            |