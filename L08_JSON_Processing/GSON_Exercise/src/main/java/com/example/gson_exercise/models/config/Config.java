package com.example.gson_exercise.models.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Random;

@Configuration
public class Config {
    @Bean
    public Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Bean
    @Primary
    public Gson createPrettyGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public Random createRandom() {
        return new Random();
    }
    @Bean
    public ModelMapper createModelMapper() {

        return new ModelMapper();
    }
}
