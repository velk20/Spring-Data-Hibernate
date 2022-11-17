package com.example.xml_exercise.models.categories.import_data;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateCategoryListDTO {
    @XmlElement(name = "category")
    private List<CreateCategoryDTO> categories;
}
