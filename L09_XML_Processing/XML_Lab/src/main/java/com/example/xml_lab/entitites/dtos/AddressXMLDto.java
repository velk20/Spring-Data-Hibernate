package com.example.xml_lab.entitites.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@ToString
@Setter
@NoArgsConstructor
@XmlRootElement(name = "address-xml-dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressXMLDto {
    @XmlElement
    private int id;
    @XmlElementWrapper(name = "countries")
    private List<CountryXMLDto> country;
    @XmlElement
    private String city;

    public AddressXMLDto(int id, CountryXMLDto country, String city) {
        this.id = id;
        this.country = List.of(country,country,country);
        this.city = city;
    }
}
