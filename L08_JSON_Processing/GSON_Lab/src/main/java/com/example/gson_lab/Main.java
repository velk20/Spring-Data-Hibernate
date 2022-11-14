package com.example.gson_lab;

import com.example.gson_lab.config.LocalDateAdapterDeSerialize;
import com.example.gson_lab.config.LocalDateAdapterSerialize;
import com.example.gson_lab.models.dtos.AddressDTO;
import com.example.gson_lab.models.dtos.CreateEmployeeDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {
    private static final Scanner scanner = new Scanner(System.in);


//    {"country":"bulgaria","city":"plovdiv"}
//    [{"country":"bulgaria","city":"plovdiv"},{"country":"bulgaria","city":"varna"},{"country":"bulgaria","city":"sofia"}]
//    {"firstName":"Angel","lastName":"Mladenov","salary":10,"birthday":"2022-11-14","address":{"country":"bulgaria","city":"plovdiv"}}

    @Override
    public void run(String... args) throws Exception {
        Gson gson = new GsonBuilder()
                //we need TypeAdapter for using dates
                //serialize
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterSerialize())
                //de-serialize
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterDeSerialize())
                .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
                .create();

        //From entities to JSON
        AddressDTO addressDTO = new AddressDTO("bulgaria", "plovdiv");
        AddressDTO addressDTO1 = new AddressDTO("bulgaria", "sofia");
        AddressDTO addressDTO2 = new AddressDTO("bulgaria", "varna");
        System.out.println(gson.toJson(addressDTO));


        List<AddressDTO> addressDTOList = List.of(addressDTO, addressDTO2, addressDTO1);
        System.out.println(gson.toJson(addressDTOList));

        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO(
                "Angel",
                "Mladenov",
                BigDecimal.TEN,
                LocalDate.now(),
                addressDTO);

        System.out.println(gson.toJson(createEmployeeDTO));

        //From json to entity

        //for one object
        String inputObject = scanner.nextLine();
        CreateEmployeeDTO parsedDTO = gson.fromJson(inputObject, CreateEmployeeDTO.class);
        System.out.println(parsedDTO);

        //for collection of objects
        String inputCollection = scanner.nextLine();
        AddressDTO[] parsedListDTO = gson.fromJson(inputCollection, AddressDTO[].class);
        Arrays.stream(parsedListDTO).toList().forEach(System.out::println);
    }



}
