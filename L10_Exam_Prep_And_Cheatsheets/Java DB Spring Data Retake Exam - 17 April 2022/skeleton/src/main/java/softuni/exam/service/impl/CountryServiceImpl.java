package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;

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
public class CountryServiceImpl implements CountryService {
    private final Path path = Path.of("src", "main", "resources", "files", "json", "countries.json");
    private final ModelMapper mapper;
    private final Gson gson;
    private final CountryRepository countryRepository;
    private final Validator validator;

    @Autowired
    public CountryServiceImpl(ModelMapper mapper, Gson gson, CountryRepository countryRepository, Validator validator) {
        this.mapper = mapper;
        this.gson = gson;
        this.countryRepository = countryRepository;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importCountries() throws IOException {
        String json = readCountriesFromFile();
        ImportCountryDTO[] importCountryDTOS = gson.fromJson(json, ImportCountryDTO[].class);

        return Arrays.stream(importCountryDTOS)
                .map(this::importCountry)
                .collect(Collectors.joining("\n"));
    }

    private String importCountry(ImportCountryDTO importCountryDTO) {
        Set<ConstraintViolation<ImportCountryDTO>> errors = validator.validate(importCountryDTO);
        if (!errors.isEmpty()) {
            return "Invalid country";
        }

        Optional<Country> byCountryName = this.countryRepository.findByCountryName(importCountryDTO.getCountryName());
        if (byCountryName.isPresent()) {
            return "Invalid country";
        }

        Country country = mapper.map(importCountryDTO, Country.class);
        this.countryRepository.save(country);
        return String.format("Successfully imported country %s - %s", country.getCountryName(), country.getCurrency());
    }
}
