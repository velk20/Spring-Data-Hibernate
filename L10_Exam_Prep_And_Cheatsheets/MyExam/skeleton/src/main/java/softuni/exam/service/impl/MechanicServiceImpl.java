package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.mechanic.ImportMechanicDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_MECHANIC;
import static softuni.exam.util.FilesPaths.MECHANICS_PATH;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final MechanicRepository mechanicRepository;

    @Autowired
    public MechanicServiceImpl(ModelMapper mapper, Gson gson, ValidationUtil validationUtil, MechanicRepository mechanicRepository) {
        this.mapper = mapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(MECHANICS_PATH);
    }

    @Override
    public String importMechanics() throws IOException {
        ImportMechanicDto[] importMechanicDtos = gson.fromJson(readMechanicsFromFile(), ImportMechanicDto[].class);
        return Arrays.stream(importMechanicDtos)
                .map(this::importMechanic)
                .collect(Collectors.joining("\n"));
    }

    private String importMechanic(ImportMechanicDto importMechanicDto) {
        if (!validationUtil.isValid(importMechanicDto)) return INVALID_MECHANIC;

        if (this.findByEmail(importMechanicDto.getEmail()) != null) return INVALID_MECHANIC;
        if (this.findByFirstName(importMechanicDto.getFirstName()) != null) return INVALID_MECHANIC;

        Mechanic mechanic = mapper.map(importMechanicDto, Mechanic.class);
        this.mechanicRepository.save(mechanic);
        return String.format("Successfully imported mechanic %s %s", mechanic.getFirstName(), mechanic.getLastName());
    }

    @Override
    public Mechanic findByFirstName(String firstName) {
        return this.mechanicRepository.findByFirstName(firstName).orElse(null);
    }

    @Override
    public Mechanic findByEmail(String email) {
        return this.mechanicRepository.findByEmail(email).orElse(null);
    }

}
