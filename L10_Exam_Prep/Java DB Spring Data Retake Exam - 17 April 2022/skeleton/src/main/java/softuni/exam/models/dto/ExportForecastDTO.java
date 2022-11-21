package softuni.exam.models.dto;

import java.util.Date;

public class ExportForecastDTO {
    private String cityName;
    private double minTemperature;
    private double maxTemperature;
    private Date sunrise;
    private Date sunset;

    public ExportForecastDTO(String cityName, double minTemperature, double maxTemperature, Date sunrise, Date sunset) {
        this.cityName = cityName;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
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

    @Override
    public String toString() {
        return String.format("City: %s:\n" +
                "-min temperature: %.2f\n" +
                "--max temperature: %.2f\n" +
                "---sunrise: %s\n" +
                "----sunset: %s",this.getCityName(),this.getMinTemperature(),this.getMaxTemperature(),
                this.getSunrise().toString(),this.getSunset().toString());

    }
}
