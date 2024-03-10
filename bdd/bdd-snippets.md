# BDD Snippets

### Basic project
````
mvn archetype:generate                      \
"-DarchetypeGroupId=io.cucumber"           \
"-DarchetypeArtifactId=cucumber-archetype" \
"-DarchetypeVersion=7.15.0"               \
"-DgroupId=com.hellocucumber"                  \
"-DartifactId=hellocucumber"               \
"-Dpackage=com.hellocucumber"                  \
"-Dversion=1.0.0"                 \
"-DinteractiveMode=false"
````
### First run
`mvn test`

### First test
* create `stack.feature`:
```gherkin
Feature: Test stack features

  Scenario: Adding element to new stack
    Given empty stack is created
    When new element is added to stack
    Then new element is at the top of the stack
```
* create `MyStack` class:
```java
import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> {

    private Deque<T> stack;

    public MyStack() {
        this.stack = new ArrayDeque<>();
    }

    public void push(T t) {
        stack.push(t);
    }

    public T pop() {
        return stack.pop();
    }

    public T top() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
```
* create `StackStepDefinitions` class:
```java
public class StackStepDefinitions {

    UnitStackable<String> unitStack;

    @Before
    public void init() {
        unitStack = new UnitStack<>();
    }
    @Given("empty stack is created")
    public void emptyStackIsCreated() {
        assertTrue(unitStack.isEmpty());
    }

    @When("new element is added to stack")
    public void newElementIsAddedToStack() {
        unitStack.push("element");
    }

    @Then("new element is at the top of the stack")
    public void newElementIsAtTheTopOfTheStack() {
        assertEquals("element", unitStack.top());
    }
}
```
* Add bad scenario:
```gherkin
  Scenario: Removing elements from the stack
    Given non-empty stack with 3 elements
    When all elements are removed from stack
    Then stack is empty
    And throws "java.util.NoSuchElementException" for getting element from empty stack
```

```java
    @Given("non-empty stack with {int} elements")
    public void nonEmptyStackWithElements(int size) {
        unitStack = new MyStack<>();
        unitStack.push("element");
        unitStack.push("element");
        unitStack.push("element");
        assertEquals(size, unitStack.size());
    }

    @When("all elements are removed from stack")
    public void allElementsAreRemovedFromStack() {
        unitStack.pop();
        unitStack.pop();
        unitStack.pop();
    }

    @Then("stack is empty")
    public void stackIsEmpty() {
        assertTrue(unitStack.isEmpty());
    }

    @And("throws {string} for getting element from empty stack")
    public void throwsForGettingElementFromEmptyStack(String clazz) throws ClassNotFoundException {
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> unitStack.pop());
        assertEquals(noSuchElementException.getClass(), forName(clazz));
    }
```

### Scenario Outlines
* create `FizzBuzzProblem` class:
```java
public class FizzBuzzProblem {
    public String getFizzBuzzNumber(int number) {
        if (number % 15 == 0) {
            return "FizzBuzz";
        } else if (number % 5 == 0) {
            return "Buzz";
        } else if (number % 3 == 0) {
            return "Fizz";
        }
        return String.valueOf(number);
    }
}
```
* create `fizzbuzz.feature` file:
```gherkin
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
```

* create `FizzBuzzStepDefinitions` class:
```java
public class FizzBuzzStepDefinitions {

    FizzBuzzProblem fizzBuzzProblem;
    String result;

    @Given("I have set of numbers")
    public void iHaveSetOfNumbers() {
        fizzBuzzProblem = new FizzBuzzProblem();
    }

    @When("I pass {int} through algorithm")
    public void iPassNumberThroughAlgorithm(int number) {
        result = fizzBuzzProblem.getFizzBuzzNumber(number);
    }

    @Then("I get {string}")
    public void iGet(String expectedResult) {
        assertEquals(expectedResult, result);
    }
}
```

### Localization
> https://cucumber.io/docs/gherkin/languages/

### Best practise
> https://cucumber.io/docs/gherkin/step-organization/?lang=java

### Browser Automation
Cucumber is not a browser automation tool, but it works well with Selenium WebDriver.

```gherkin
Feature: Download file from github

  Scenario: Download maven-presentation from github
    Given I am on ZZPJ Github page
    When I am on "main" branch of page
    Then I go to "intro" folder
    And I click on "ZZPJ2021-maven.pdf" link
    Then I click "Download" button to get the file
    And the file is on my disk
```

```java
public class GitHubStepDefinitions {
    static WebDriver driver;

    @Before
    public static void beforeEachScenario() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
    }

    @Given("I am on ZZPJ Github page")
    public void iAmOnZZPJGithubPage() {
        //driver.get("https://www.google.com");
        driver.get("https://github.com/zzpj/pl-java2023");
    }

    @When("I am on {string} branch of page")
    public void iAmOnBranchOfPage(String branchName) {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"branch-select-menu\"]/summary/span[1]"));
        String expectedBranchName = element.getText();
        assertEquals(expectedBranchName, branchName);
    }

    @Then("I go to {string} folder")
    public void iGoToFolder(String folderName) {
        WebElement element = driver.findElement(By.linkText(folderName));
        element.click();
    }

    @And("I click on {string} link")
    public void iClickOnLink(String linkName) {
        WebElement element = driver.findElement(By.linkText(linkName));
        element.click();
    }

    @Then("I click {string} button to get the file")
    public void iClickButtonToGetTheFile(String downloadButton) {
        WebElement element = driver.findElement(By.id("raw-url"));
        assertEquals(downloadButton, element.getText());
        element.click();
    }

    @And("the file is on my disk")
    public void theFileIsOnMyDisk() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d ->
                new File("C:\\Users\\Zbyszko\\Downloads\\ZZPJ2021-maven.pdf").exists()
        );
    }

    @After()
    public void closeBrowser() {
        driver.close();
        driver.quit();
    }
```
