package softuni.exam.models.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastRootDTO {
    @XmlElement(name = "forecast")
    private List<ForecastDTO> forecasts;

    public List<ForecastDTO> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastDTO> forecasts) {
        this.forecasts = forecasts;
    }
}
