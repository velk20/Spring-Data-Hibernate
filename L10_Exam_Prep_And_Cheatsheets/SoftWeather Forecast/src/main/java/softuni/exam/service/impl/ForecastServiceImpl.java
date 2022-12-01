package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastDTO;
import softuni.exam.models.dto.ImportForecastRootDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_FORECAST;
import static softuni.exam.util.FilesPaths.FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final CityService cityService;
    private final ForecastRepository forecastRepository;

    public ForecastServiceImpl(XmlParser xmlParser, ModelMapper mapper, ValidationUtil validationUtil, CityService cityService, ForecastRepository forecastRepository) {
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.cityService = cityService;
        this.forecastRepository = forecastRepository;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(FORECASTS_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        ImportForecastRootDTO importForecastRootDTO = xmlParser.fromFile(FORECASTS_PATH.toAbsolutePath().toString(), ImportForecastRootDTO.class);
        return importForecastRootDTO.getForecasts()
                .stream()
                .map(this::importForecast)
                .collect(Collectors.joining("\n"));
    }

    private String importForecast(ForecastDTO forecastDTO) {
        if (!validationUtil.isValid(forecastDTO)) return INVALID_FORECAST;

        City cityServiceByCityId = cityService.findByCityId(forecastDTO.getCity());
        if (cityServiceByCityId == null) {
            return INVALID_FORECAST;
        }

        Optional<Forecast> byCityAndDayOfWeek = this.forecastRepository.findByCityAndDayOfWeek(cityServiceByCityId, forecastDTO.getDayOfWeek());
        if (byCityAndDayOfWeek.isPresent()) {
            return INVALID_FORECAST;
        }

        Forecast forecast = mapper.map(forecastDTO, Forecast.class);

        forecast.setCity(cityServiceByCityId);
        this.forecastRepository.save(forecast);
        return String.format("Successfully import forecast %s - %.2f", forecastDTO.getDayOfWeek(), forecastDTO.getMaxTemperature());
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
