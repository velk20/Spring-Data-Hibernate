package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportTownDTO;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.FilesPaths;
import softuni.exam.util.ValidationUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.util.FilesPaths.*;

@Service
public class TownServiceImpl implements TownService {
    private final ModelMapper mapper;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validator;
    @Autowired
    public TownServiceImpl(ModelMapper mapper, TownRepository townRepository, Gson gson, ValidationUtil validator) {
        this.mapper = mapper;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_PATH);
    }

    @Override
    public String importTowns() throws IOException {
        String json = readTownsFileContent();
        ImportTownDTO[] importTownDTOS = gson.fromJson(json, ImportTownDTO[].class);

        return Arrays.stream(importTownDTOS)
                .map(this::importTown)
                .collect(Collectors.joining("\n"));
    }

    private String importTown(ImportTownDTO importTownDTO) {
        if (!validator.isValid(importTownDTO)) {
            return "Invalid town";
        }

        this.townRepository.save(mapper.map(importTownDTO, Town.class));
        return String.format("Successfully imported town %s - %d", importTownDTO.getTownName(), importTownDTO.getPopulation());
    }

}
