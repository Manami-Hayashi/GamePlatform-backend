package be.kdg.prog6.chatbotContext.domain;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JsonFileReader {
    public static String readJsonFile(String fileName) {
        InputStream inputStream = JsonFileReader.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        // Convert InputStream to String
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next(); // Read whole file as a single String
        }
    }
}
