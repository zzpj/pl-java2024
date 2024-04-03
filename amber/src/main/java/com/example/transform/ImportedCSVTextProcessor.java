package com.example.transform;

public class ImportedCSVTextProcessor {

    String process(String line) {
        return line.replace(',', ' ')
                .replace("none", "")
                .trim()
                .transform(e -> e + "-done")
                .toUpperCase();
    }
}
