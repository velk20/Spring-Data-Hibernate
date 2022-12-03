package softuni.exam.models.dto;

import java.math.BigDecimal;

public class ExportHighestPriceTasksDto {
    private String carMake;
    private String carModel;
    private Integer kilometers;
    private String firstName;
    private String lastName;
    private Long taskId;
    private Double engine;
    private BigDecimal taskPrice;

    public ExportHighestPriceTasksDto(String carMake, String carModel, Integer kilometers, String firstName, String lastName, Long taskId, Double engine, BigDecimal taskPrice) {

        this.carMake = carMake;
        this.carModel = carModel;
        this.kilometers = kilometers;
        this.firstName = firstName;
        this.lastName = lastName;
        this.taskId = taskId;
        this.engine = engine;
        this.taskPrice = taskPrice;
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

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Double getEngine() {
        return engine;
    }

    public void setEngine(Double engine) {
        this.engine = engine;
    }

    public BigDecimal getTaskPrice() {
        return taskPrice;
    }

    public void setTaskPrice(BigDecimal taskPrice) {
        this.taskPrice = taskPrice;
    }

    @Override
    public String toString() {
        return String.format(
                "Car %s %s with %dkm\n" +
                "-Mechanic: %s %s - task №%d:\n" +
                " --Engine: %.2f\n" +
                "---Price: %.2f$", carMake, carModel, kilometers, firstName, lastName, taskId, engine, taskPrice);
    }
}
