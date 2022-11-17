package com.example.xml_exercise.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {

    void seedProducts() throws JAXBException, IOException;

    void productsInRange(BigDecimal start, BigDecimal end) throws Exception;
}
