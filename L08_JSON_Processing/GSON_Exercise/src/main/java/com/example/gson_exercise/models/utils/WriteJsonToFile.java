package com.example.gson_exercise.models.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class WriteJsonToFile {
    public static String writeToFile(String data, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(data);
        writer.close();
        return data;
    }
}
