Feature: Datatable

  Scenario: Example with data table
    Given user info data table
      | username       | email              | secret panel access | first login date | priority level |
      | admin          | admin@validmail.pl | true                | 01-01-1970       | 10             |
      | moderator      | mod@validmail.pl   | true                | 01-01-2000       | 8              |
      | regular_user   | franek@p.lodz.pl   | false               | 01-02-2010       | 5              |
      | different_user | adam@p.lodz.pl     | false               | 01-02-2010       | 4              |
      | blocked_user   | ban@validmai       | false               | 01-02-2005       | -1             |
    Then only 2 users "admin" and "moderator" has access to secret panel
    And "blocked_user" has invalid mail