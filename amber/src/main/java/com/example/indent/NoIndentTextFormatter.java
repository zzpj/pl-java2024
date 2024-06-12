package com.example.indent;

import java.util.stream.Collectors;

public class NoIndentTextFormatter {

    String noIndentText(String text) {
        // I do not like this solution
        return text.lines().map(String::stripLeading).collect(Collectors.joining("\n")) + '\n';
    }
}
