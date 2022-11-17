package com.example.xml_exercise.config;

import com.example.xml_exercise.models.categories.import_data.CreateCategoryDTO;
import com.example.xml_exercise.models.categories.import_data.CreateCategoryListDTO;
import com.example.xml_exercise.models.products.import_data.CreateProductDTO;
import com.example.xml_exercise.models.products.import_data.CreateProductListDTO;
import com.example.xml_exercise.models.users.import_data.CreateUsersListDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Random;

@Configuration
public class Config {
    @Bean("createUsers")
    public JAXBContext createUserJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(CreateUsersListDTO.class);
    }

    @Bean("createCategory")
    public JAXBContext createCategoryJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(CreateCategoryListDTO.class);
    }

    @Bean("createProduct")
    public JAXBContext createProductJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(CreateProductListDTO.class);
    }

    @Bean
    public Random createRandom() {
        return new Random();
    }
    @Bean
    public ModelMapper createModelMapper() {

        return new ModelMapper();
    }
}
