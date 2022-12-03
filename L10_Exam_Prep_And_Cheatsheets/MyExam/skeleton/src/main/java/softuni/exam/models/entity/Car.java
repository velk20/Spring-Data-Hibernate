package softuni.exam.models.entity;

import softuni.exam.models.entity.enums.CarType;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarType carType;
    @Column(nullable = false)
    private String carMake;
    @Column(nullable = false)
    private String carModel;
    @Column(nullable = false)
    private Integer year;
    @Column(nullable = false, unique = true)
    private String plateNumber;
    @Column(nullable = false)
    private Integer kilometers;
    @Column(nullable = false)
    private Double engine;

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public Double getEngine() {
        return engine;
    }

    public void setEngine(Double engine) {
        this.engine = engine;
    }
}
