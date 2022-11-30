package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_COUNTRY;
import static softuni.exam.util.FilesPaths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper mapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(COUNTRIES_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        ImportCountryDTO[] importCountryDTO = gson.fromJson(readCountriesFromFile(), ImportCountryDTO[].class);
        return Arrays.stream(importCountryDTO)
                .map(this::importCountry)
                .collect(Collectors.joining("\n"));
    }

    private String importCountry(ImportCountryDTO importCountryDTO) {
        if (!validationUtil.isValid(importCountryDTO)) return INVALID_COUNTRY;
        if (findByCountryName(importCountryDTO.getCountryName())!=null) return INVALID_COUNTRY;

        Country country = mapper.map(importCountryDTO, Country.class);
        this.countryRepository.save(country);
        return String.format("Successfully imported country %s - %s", country.getName(), country.getCurrency());
    }

    @Override
    public Country findByCountryName(String countryName) {
        return this.countryRepository.findByCountryName(countryName).orElse(null);
    }

    @Override
    public Country findByCountryId(long countryId) {
        return this.countryRepository.findById(countryId).orElse(null);
    }

}
