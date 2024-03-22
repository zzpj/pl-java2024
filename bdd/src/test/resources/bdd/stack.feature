Feature: Test stack features

  Scenario: Adding element to new stack
    Given empty stack is created
    When new element is added to stack
    Then new element is at the top of the stack

  Scenario: Removing elements from the stack
    Given non-empty stack with 3 elements
    When all elements are removed from stack
    Then stack is empty
    And throws "java.util.NoSuchElementException" for getting element from empty stack