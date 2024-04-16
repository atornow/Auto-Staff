package com.truevanilla.plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

public class filepath {
    public static void main(String[] args) {
        String fileName = "storage.txt";
        Path filePath = Paths.get("src", "main", "java", "com", "truevanilla", "plugin", fileName);
        String absolutePath = filePath.toAbsolutePath().toString();

        System.out.println("File path: " + absolutePath);
    }
}

