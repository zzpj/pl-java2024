package com.example.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    boolean isFileContainsText(Path path, String searchingText) throws IOException {
        var fileContent = Files.readString(path);
        return fileContent.contains(searchingText);
    }
}
