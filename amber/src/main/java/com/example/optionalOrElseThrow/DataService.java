package com.example.optionalOrElseThrow;

import java.util.Optional;

public class DataService {
    DataProvider dataProvider = new DataProvider();

    String getUsername(int id) {
        Optional<String> username = dataProvider.getUsername(id);
        return username.orElseThrow();
    }
}
