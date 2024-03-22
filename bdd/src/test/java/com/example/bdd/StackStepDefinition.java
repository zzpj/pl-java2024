import com.example.*;
import io.cucumber.java.*;
import io.cucumber.java.en.*;

import java.util.*;

import static java.lang.Class.*;
import static org.junit.jupiter.api.Assertions.*;

public class StackStepDefinition {

    MyStack<String> stack;

    @Before
    public void init() {
        stack = new MyStack<>();
    }

    @Given("empty stack is created")
    public void emptyStackIsCreated() {
        assertTrue(stack.isEmpty());
    }

    @When("new element is added to stack")
    public void newElementIsAddedToStack() {
        stack.push("sss");
    }

    @Then("new element is at the top of the stack")
    public void newElementIsAtTheTopOfTheStack() {
        assertEquals("sss", stack.top());
    }

    @Given("non-empty stack with {int} elements")
    public void nonEmptyStackWithElements(int size) {
        stack = new MyStack<>();
        stack.push("element");
        stack.push("element");
        stack.push("element");
        assertEquals(size, stack.size());
    }

    @When("all elements are removed from stack")
    public void allElementsAreRemovedFromStack() {
        stack.pop();
        stack.pop();
        stack.pop();
    }

    @Then("stack is empty")
    public void stackIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @And("throws {string} for getting element from empty stack")
    public void throwsForGettingElementFromEmptyStack(String clazz) throws ClassNotFoundException {
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> stack.pop());
        assertEquals(noSuchElementException.getClass(), forName(clazz));
    }
}
