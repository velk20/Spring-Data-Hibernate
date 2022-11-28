package exam.model.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopInformationDTO {
    @XmlElement
    @NotNull
    @Size(min = 4)
    private String address;
    @XmlElement(name = "employee-count")
    @NotNull
    @Min(1)
    @Max(50)
    private String employeeCount;
    @XmlElement
    @NotNull
    @Min(20000)
    private BigDecimal income;
    @XmlElement(name = "shop-area")
    @NotNull
    @Min(150)
    private int shopArea;
    @XmlElement
    @NotNull
    @Size(min = 4)
    private String name;
    @XmlElement
    @NotNull
    private TownNameDTO town;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public int getShopArea() {
        return shopArea;
    }

    public void setShopArea(int shopArea) {
        this.shopArea = shopArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TownNameDTO getTown() {
        return town;
    }

    public void setTown(TownNameDTO town) {
        this.town = town;
    }
}
