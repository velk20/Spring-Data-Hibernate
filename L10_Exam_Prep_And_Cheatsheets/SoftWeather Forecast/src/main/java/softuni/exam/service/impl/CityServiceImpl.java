package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_CITY;
import static softuni.exam.util.FilesPaths.CITIES_PATH;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public CityServiceImpl(CityRepository cityRepository, CountryService countryService, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(CITIES_PATH);
    }

    @Override
    public String importCities() throws IOException {
        ImportCityDTO[] importCityDTOS = gson.fromJson(readCitiesFileContent(), ImportCityDTO[].class);
        return Arrays.stream(importCityDTOS)
                .map(this::importCity)
                .collect(Collectors.joining("\n"));
    }

    private String importCity(ImportCityDTO importCityDTO) {
        if (!validationUtil.isValid(importCityDTO)) return INVALID_CITY;

        if (this.cityRepository.findByCityName(importCityDTO.getCityName()).isPresent()) return INVALID_CITY;

        Country byCountryId = this.countryService.findByCountryId(importCityDTO.getCountry());
        if (byCountryId == null) {
            return INVALID_CITY;
        }

        City city = mapper.map(importCityDTO, City.class);
        city.setCountry(byCountryId);

        this.cityRepository.save(city);
        return String.format("Successfully imported city %s - %d", city.getCityName(), city.getPopulation());
    }

    @Override
    public City findByCityName(String cityName) {
        return this.cityRepository.findByCityName(cityName).orElse(null);
    }
}
