package com.example.transform;

public class PalindromeChecker {

    boolean isPalindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }
}
