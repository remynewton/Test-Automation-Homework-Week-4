package com.laba.solvd.hw;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogReader {

    public void readLogFile(String logFilePath) {
        Path path = Paths.get(logFilePath);
        try {
            // Read all lines from the log file and print them to the console
            Files.lines(path, StandardCharsets.UTF_8).forEach(System.out::println);
        } catch(IOException e) {
            System.err.println("Failed to read log file: " + e.getMessage());
        }
    }
}