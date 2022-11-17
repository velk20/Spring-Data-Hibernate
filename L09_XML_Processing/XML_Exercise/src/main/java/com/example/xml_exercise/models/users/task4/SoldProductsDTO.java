package com.example.xml_exercise.models.users.task4;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDTO {
    @XmlAttribute(name = "count")
    private int count;
    @XmlElement(name = "product")
    private List<ProductInfoDTO> products;

    public SoldProductsDTO(List<ProductInfoDTO> products) {
        this.products = products;
        this.count = products.size();

    }

    public void setProducts(List<ProductInfoDTO> products) {
        this.products = products;
        this.count = products.size();
    }
}
