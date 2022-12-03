package softuni.exam.models.dto.car;

import softuni.exam.models.entity.enums.CarType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCarDto {
    @XmlElement
    @NotNull
    @Size(min = 2,max = 30)
    private String carMake;
    @XmlElement
    @NotNull
    @Size(min = 2,max = 30)
    private String carModel;
    @XmlElement
    @NotNull
    @Positive
    private Integer year;
    @XmlElement
    @NotNull
    @Size(min = 2,max = 30)
    private String plateNumber;
    @XmlElement
    @NotNull
    @Positive
    private Integer kilometers;
    @XmlElement
    @NotNull
    @DecimalMin(value = "1.00")
    private Double engine;
    @XmlElement
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarType carType;

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public Integer getYear() {
        return year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public Double getEngine() {
        return engine;
    }

    public CarType getCarType() {
        return carType;
    }
}
