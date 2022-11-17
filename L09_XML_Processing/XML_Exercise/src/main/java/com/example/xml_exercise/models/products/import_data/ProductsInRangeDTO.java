package com.example.xml_exercise.models.products.import_data;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeDTO {
    @XmlElement(name = "product")
    private List<ProductInRangeDTO> products;
}
