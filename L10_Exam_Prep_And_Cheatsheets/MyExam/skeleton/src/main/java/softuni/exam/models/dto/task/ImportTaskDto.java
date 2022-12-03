package softuni.exam.models.dto.task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTaskDto {
    @XmlElement(name = "date")
    @NotNull
    private String date;
    @XmlElement(name = "price")
    @NotNull
    @Positive
    private BigDecimal price;
    @XmlElement(name = "car")
    @NotNull
    private ImportTaskCarDto car;
    @XmlElement(name = "mechanic")
    @NotNull
    private ImportTaskMechanicDto mechanic;
    @XmlElement(name = "part")
    @NotNull
    private ImportTaskPartDto part;

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ImportTaskCarDto getCar() {
        return car;
    }

    public ImportTaskMechanicDto getMechanic() {
        return mechanic;
    }

    public ImportTaskPartDto getPart() {
        return part;
    }
}
