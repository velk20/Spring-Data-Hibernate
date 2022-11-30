package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.service.ForecastService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import static softuni.exam.util.ErrorMessages.INVALID_COUNTRY;
import static softuni.exam.util.FilesPaths.COUNTRIES_PATH;
@Service
public class ForecastServiceImpl implements ForecastService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return null;
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        return null;
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
