package softuni.exam.models.dtos;

import softuni.exam.models.entities.ApartmentType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentDTO {
    @XmlElement(name = "apartmentType")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private ApartmentType apartmentType;

    @XmlElement(name = "area")
    @DecimalMin("40.00")
    private double area;

    @XmlElement(name = "town")
    @NotNull
    private String townName;

    public ImportApartmentDTO() {
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
