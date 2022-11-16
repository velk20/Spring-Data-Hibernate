package com.example.gson_exercise.services;

import com.example.gson_exercise.models.dtos.CreateProductDTO;
import com.example.gson_exercise.models.dtos.ExportProductDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public interface ProductService {
    CreateProductDTO[] readAllProductsFromFile() throws IOException;

    void saveProduct(CreateProductDTO createProductDTO);

    String productsInRange(BigDecimal startRange, BigDecimal endRange);

}
