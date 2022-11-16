package com.example.xml_lab.entitites.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryXMLDto {
    @XmlAttribute
    private String value;

    public CountryXMLDto(String value) {
        this.value = value;
    }
}
