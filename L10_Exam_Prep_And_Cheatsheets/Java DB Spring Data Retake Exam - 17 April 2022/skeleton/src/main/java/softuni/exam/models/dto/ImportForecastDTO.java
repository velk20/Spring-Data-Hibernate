package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastDTO {
    @XmlElement(name = "forecast")
    private List<ForecastInfoDTO> forecasts;

    public List<ForecastInfoDTO> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastInfoDTO> forecasts) {
        this.forecasts = forecasts;
    }
}
