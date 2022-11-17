package com.example.xml_exercise.models.categories.import_data;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateCategoryDTO {
    @XmlElement
    private String name;
}
