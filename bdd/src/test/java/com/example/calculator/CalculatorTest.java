package com.example.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/calculator.csv", numLinesToSkip = 1, delimiter = ';')
    void shouldCheckDivibility(int a, int b, int sum) {
        //TODO: implement
    }
}