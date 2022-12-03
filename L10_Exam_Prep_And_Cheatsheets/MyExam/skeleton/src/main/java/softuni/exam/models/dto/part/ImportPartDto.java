package softuni.exam.models.dto.part;

import javax.validation.constraints.*;

public class ImportPartDto {
    @Size(min = 2,max = 19)
    @NotNull
    private String partName;
    @Min(10)
    @Max(2000)
    @NotNull
    private Double price;
    @Positive
    @NotNull
    private Integer quantity;

    public String getPartName() {
        return partName;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
