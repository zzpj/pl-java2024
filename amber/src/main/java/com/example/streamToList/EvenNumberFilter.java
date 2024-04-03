package com.example.streamToList;

import java.util.List;
import java.util.stream.Collectors;

public class EvenNumberFilter {

    List<Integer> getEvenNumbers(List<Integer> allNumbers) {
        var numbers = allNumbers.stream();
        numbers = numbers.filter(n -> n % 2 == 0);
        return numbers.collect(Collectors.toList());
    }
}
