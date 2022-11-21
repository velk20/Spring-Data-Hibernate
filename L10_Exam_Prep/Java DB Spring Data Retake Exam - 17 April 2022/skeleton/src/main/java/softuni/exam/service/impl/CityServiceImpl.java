package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final Path path = Path.of("src", "main", "resources", "files", "json", "cities.json");
    private final ModelMapper mapper;
    private final Gson gson;
    private final Validator validator;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(ModelMapper mapper, Gson gson, Validator validator, CityRepository cityRepository, CountryRepository countryRepository) {
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
         return Files.readString(path);
    }

    @Override
    public String importCities() throws IOException {
        String json = readCitiesFileContent();
        ImportCityDTO[] importCityDTOS = gson.fromJson(json, ImportCityDTO[].class);

        return Arrays.stream(importCityDTOS)
                .map(this::importCity)
                .collect(Collectors.joining("\n"));
    }

    private String importCity(ImportCityDTO importCityDTO) {
        Set<ConstraintViolation<ImportCityDTO>> validate =
                validator.validate(importCityDTO);
        if (!validate.isEmpty()) {
            return "Invalid city";
        }

        Optional<City> byName = this.cityRepository.findByCityName(importCityDTO.getCityName());
        if (byName.isPresent()) {
            return "Invalid city";
        }

        Optional<Country> countryById = this.countryRepository.findById(importCityDTO.getCountry());
        if (countryById.isEmpty()) {
            return "Invalid city";
        }

        City city = mapper.map(importCityDTO, City.class);
        city.setCountry(countryById.get());

        this.cityRepository.save(city);
        return String.format("Successfully imported city %s - %d", city.getCityName(), city.getPopulation());
    }
}
