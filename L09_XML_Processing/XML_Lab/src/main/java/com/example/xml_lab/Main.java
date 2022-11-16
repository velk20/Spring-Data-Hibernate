package com.example.xml_lab;

import com.example.xml_lab.entitites.dtos.AddressXMLDto;
import com.example.xml_lab.entitites.dtos.CountryXMLDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Component
public class Main implements CommandLineRunner {


    public final JAXBContext addressContext;
    public final JAXBContext countryContext;

    @Autowired
    public Main(@Qualifier("addressContext") JAXBContext addressContext, JAXBContext countryContext) {
        this.addressContext = addressContext;
        this.countryContext = countryContext;
    }

    @Override
    public void run(String... args) throws Exception {

        AddressXMLDto xmlDto = new AddressXMLDto(5, new CountryXMLDto("Bulgaria"), "Plovdiv");

        //Object to XML
        JAXBContext jaxbContext = JAXBContext.newInstance(AddressXMLDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(xmlDto, System.out);

        //XML to Object
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        AddressXMLDto unmarshal = (AddressXMLDto) unmarshaller.unmarshal(System.in);

        System.out.println(unmarshal);
    }
}
