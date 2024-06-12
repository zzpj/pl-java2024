package com.example.stringMethods;

import java.util.List;
import java.util.stream.Collectors;

public class StringService {

    List<String> getOnlyNotBlankStrings(List<String> input) {
        var strings = input.stream();
        strings = strings.filter(n -> !n.isBlank());
        return strings.collect(Collectors.toList());
    }

    List<String> getStrippedTextLines(String text) {
        return text.lines().map(String::strip).toList();
    }

    List<String> extendFoundStringByRepeatSomeTimes(List<String> list, String searchedText, int nTimesRepeat) {
        return list.stream().map(string -> {
            if (string.equals(searchedText)) {
                return string.repeat(nTimesRepeat);
            } else {
                return string;
            }
        }).toList();
    }
}
