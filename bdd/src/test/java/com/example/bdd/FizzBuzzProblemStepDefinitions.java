package com.example.bdd;

import com.example.*;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class FizzBuzzProblemStepDefinitions {
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
