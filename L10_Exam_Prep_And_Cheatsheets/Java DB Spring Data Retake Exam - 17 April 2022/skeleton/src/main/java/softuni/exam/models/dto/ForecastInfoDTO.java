package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.DayOfWeek;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastInfoDTO {
    @XmlElement(name = "day_of_week")
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;
    @XmlElement(name = "max_temperature")
    @Min(-20)
    @Max(60)
    @NotNull
    private double maxTemperature;
    @XmlElement(name = "min_temperature")
    @Min(-50)
    @Max(40)
    @NotNull
    private double minTemperature;
    @XmlElement
    private Date sunrise;
    @XmlElement
    private Date sunset;
    @XmlElement
    private long city;

    public ForecastInfoDTO() {
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }
}

