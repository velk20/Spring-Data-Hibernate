package com.example.xml_exercise.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CategoryService {

    void seedCategories() throws JAXBException, IOException;

    void getAllCategoriesByProductsCount() throws JAXBException;
}
