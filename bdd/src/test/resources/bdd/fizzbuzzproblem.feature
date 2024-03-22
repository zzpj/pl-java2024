Feature: FizzBuzzProblem

  Scenario Outline: Solve Fizz Buzz Problem
    Given I have set of numbers
    When I pass <number> through algorithm
    Then I get "<answer>"

    Examples:
      | number | answer   |
      | 3      | Fizz     |
      | 4      | 4        |
      | 5      | Buzz     |
      | 15     | FizzBuzz |

