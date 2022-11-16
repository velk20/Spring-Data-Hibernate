package com.example.gson_exercise.models.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class ReadJsonFromFile {
    public static String readFromFile(String path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(path));
        List<String> lines = new ArrayList<>();
        String json;
        while ((json = reader.readLine()) != null) {
            lines.add(json.trim());
        }
        return lines.isEmpty()? null : String.join("",lines);
    }
}
