package com.example.xml_exercise.models.products.import_data;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateProductDTO {
    @XmlElement
    private String name;
    @XmlElement
    private BigDecimal price;
}
