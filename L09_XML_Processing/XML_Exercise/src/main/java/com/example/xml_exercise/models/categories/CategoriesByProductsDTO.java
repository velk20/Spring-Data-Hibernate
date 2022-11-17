package com.example.xml_exercise.models.categories;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesByProductsDTO {
    @XmlElement(name = "category")
    List<CategoryAndProductsInfoDTO> categories;
}

