package com.example.fileMismatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEqualsChecker {

    boolean isFileEquals(Path path1, Path path2) throws IOException {
        return Files.mismatch(path1, path2) == -1L;
    }
}
