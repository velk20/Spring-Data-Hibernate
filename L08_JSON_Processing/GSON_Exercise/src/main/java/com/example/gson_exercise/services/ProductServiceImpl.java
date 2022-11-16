package com.example.gson_exercise.services;

import com.example.gson_exercise.models.dtos.ExportProductDTO;
import com.example.gson_exercise.models.entities.Category;
import com.example.gson_exercise.models.utils.FilesPaths;
import com.example.gson_exercise.models.dtos.CreateProductDTO;
import com.example.gson_exercise.models.entities.Product;
import com.example.gson_exercise.models.utils.ReadJsonFromFile;
import com.example.gson_exercise.repositories.CategoryRepository;
import com.example.gson_exercise.repositories.ProductRepository;
import com.example.gson_exercise.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final Random random;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper mapper, Gson gson, Random random) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.random = random;
    }

    @Override
    public CreateProductDTO[] readAllProductsFromFile() throws IOException {
        String json = ReadJsonFromFile.readFromFile(FilesPaths.PRODUCTS_FILE_PATH);
        return gson.fromJson(json, CreateProductDTO[].class);
    }

    @Override
    public void saveProduct(CreateProductDTO createProductDTO) {
        Product product = mapper.map(createProductDTO, Product.class);
        long usersCount = this.userRepository.count();
        long randomUserBuyerId = random.nextLong(1, usersCount);
        long randomUserSellerId = random.nextLong(1, usersCount);

        product.setSeller(this.userRepository.findById(randomUserSellerId).get());
        if (!createProductDTO.getName().startsWith("A")) {
            product.setBuyer(this.userRepository.findById(randomUserBuyerId).get());
        }

        List<Category> categoryRepositoryAll = this.categoryRepository.findAll();
        product.setCategories(new HashSet<>(){{
            add(categoryRepositoryAll.get((int)random.nextLong(1,categoryRepository.count())));
            add(categoryRepositoryAll.get((int)random.nextLong(1,categoryRepository.count())));
        }});
        this.productRepository.save(product);
    }

    @Override
    public String productsInRange(BigDecimal startRange, BigDecimal endRange) {
        List<ExportProductDTO> exportProductDTOS = this.productRepository.productsInRange(startRange, endRange);
        Gson gsonOutput = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        return gsonOutput.toJson(exportProductDTOS);
    }

}
