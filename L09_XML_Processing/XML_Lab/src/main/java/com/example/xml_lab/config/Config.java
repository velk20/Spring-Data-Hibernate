package com.example.xml_lab.config;

import com.example.xml_lab.entitites.dtos.AddressXMLDto;
import com.example.xml_lab.entitites.dtos.CountryXMLDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
public class Config {
    @Bean("addressContext")
    public JAXBContext createAddressContext() throws JAXBException {
        return JAXBContext.newInstance(AddressXMLDto.class);
    }

    @Bean("countryContext")
    public JAXBContext createCountryContext() throws JAXBException {
        return JAXBContext.newInstance(CountryXMLDto.class);
    }
}
