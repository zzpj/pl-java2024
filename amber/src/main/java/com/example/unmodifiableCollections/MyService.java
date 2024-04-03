package com.example.unmodifiableCollections;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MyService {
    Some3rdService some3rdService = new Some3rdService();

    public List<Integer> getUnmodifiableListByCollector() {
        return some3rdService.getList().stream().collect(Collectors.toUnmodifiableList());
    }

    public List<Integer> getUnmodifiableListByCopy() {
        return List.copyOf(some3rdService.getList());
    }
}
