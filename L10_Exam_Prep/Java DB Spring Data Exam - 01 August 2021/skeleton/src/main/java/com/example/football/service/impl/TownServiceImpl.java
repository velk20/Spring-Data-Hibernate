package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, Validator validator, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");
//        return String.join("\n", Files.readAllLines(path));
        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {
        String json = this.readTownsFileContent();
        ImportTownDTO[] importTownDTOS = this.gson.fromJson(json, ImportTownDTO[].class);

        return Arrays.stream(importTownDTOS)
                .map(this::importTown)
                .collect(Collectors.joining("\n"));

    }

    private String importTown(ImportTownDTO townDTO) {
        Set<ConstraintViolation<ImportTownDTO>> validationErrors = this.validator.validate(townDTO);

        if (!validationErrors.isEmpty()) {
            return "Invalid Town";
        }

        Optional<Town> isTownExist = this.townRepository.findByName(townDTO.getName());

        if (isTownExist.isPresent()) {
            return "Invalid Town";
        }
        Town town = this.modelMapper.map(townDTO, Town.class);
        this.townRepository.save(town);
        return String.format("Successfully imported Town %s - %d", town.getName(), town.getPopulation());
    }

}
