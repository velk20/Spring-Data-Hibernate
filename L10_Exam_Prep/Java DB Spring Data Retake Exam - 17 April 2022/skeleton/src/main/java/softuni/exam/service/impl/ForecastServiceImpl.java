package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportForecastDTO;
import softuni.exam.models.dto.ForecastInfoDTO;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DayOfWeek;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "forecasts.xml");
    private final ModelMapper mapper;
    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final JAXBContext jaxbContext;
    private final Validator validator;


    @Autowired
    public ForecastServiceImpl(ModelMapper mapper, ForecastRepository forecastRepository, CityRepository cityRepository, Validator validator) throws JAXBException {
        this.mapper = mapper;
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.validator = validator;
        this.jaxbContext = JAXBContext.newInstance(ImportForecastDTO.class);

    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ImportForecastDTO importForecastDTO = (ImportForecastDTO) unmarshaller.unmarshal(Files.newBufferedReader(path));
        return importForecastDTO.getForecasts()
                .stream()
                .map(this::importForecast)
                .collect(Collectors.joining("\n"));
    }

    private String importForecast(ForecastInfoDTO forecastInfoDTO) {
        Set<ConstraintViolation<ForecastInfoDTO>> errors = validator.validate(forecastInfoDTO);
        if (!errors.isEmpty()) {
            return "Invalid forecast";
        }

        Optional<City> cityById = this.cityRepository.findById(forecastInfoDTO.getCity());
        if (cityById.isEmpty()) {
            return "Invalid forecast";
        }

        Optional<Forecast> repositoryByDayOfWeekAndCity = this.forecastRepository.findByDayOfWeekAndCity(forecastInfoDTO.getDayOfWeek(), cityById.get());
        if (repositoryByDayOfWeekAndCity.isPresent()) {
            return "Invalid forecast";
        }

        Forecast forecast = mapper.map(forecastInfoDTO, Forecast.class);
        forecast.setCity(cityById.get());
        this.forecastRepository.save(forecast);
        return String.format("Successfully import forecast %s - %.2f", forecast.getDayOfWeek().name(),
                forecast.getMaxTemperature());
    }
    @Override
    public String exportForecasts() {
        List<ExportForecastDTO> exportForecastDTOS = this.forecastRepository.exportAllByDayOfWeek(DayOfWeek.SUNDAY);

        return exportForecastDTOS.stream().map(ExportForecastDTO::toString).collect(Collectors.joining("\n"));
    }
}
