package com.example.gson_lab.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Scanner createScanner() {
        return new Scanner(System.in);
    }
@Bean
    public Gson createGson() {
   return  new GsonBuilder()
            //we need TypeAdapter for using dates
            //serialize
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapterSerialize())
            //de-serialize
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapterDeSerialize())
            .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
            .create();
}
}
