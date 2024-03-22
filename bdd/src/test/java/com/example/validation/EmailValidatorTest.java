package com.example.validation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {
    // TODO Write test for EmailValidator
    // The names of the methods should give you a pointer what to test for

    @Test
    @Disabled
    public void ensureThatEmailValidatorReturnsTrueForValidEmail() {
        assertTrue(EmailValidator.isValidEmail("lars.vogel@gmail.com"));
    }

    @Test
    @Disabled
    @DisplayName("Ensure that the usage of a subdomain is still valid, see https://en.wikipedia.org/wiki/Subdomain")
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
    }

    @Test
    @Disabled
    @DisplayName("Ensure that a missing top level domain returns false")
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
    }

    @Test
    @Disabled
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
    }

    @Test
    @Disabled
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
    }

    @Test
    @Disabled
    public void emailValidator_EmptyString_ReturnsFalse() {
    }

    @Test
    @Disabled
    public void emailValidator_NullEmail_ReturnsFalse() {
    }
}