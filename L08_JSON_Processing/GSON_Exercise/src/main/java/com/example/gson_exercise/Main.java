package com.example.gson_exercise;

import com.example.gson_exercise.models.dtos.CreateCategoryDTO;
import com.example.gson_exercise.models.dtos.CreateProductDTO;
import com.example.gson_exercise.models.dtos.CreateUserDTO;
import com.example.gson_exercise.models.utils.FilesPaths;
import com.example.gson_exercise.services.CategoryService;
import com.example.gson_exercise.services.ProductService;
import com.example.gson_exercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

import static com.example.gson_exercise.models.utils.WriteJsonToFile.writeToFile;

@Component
public class Main implements CommandLineRunner {
    private CategoryService categoryService;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public Main(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }



    @Override
    public void run(String... args) throws Exception {
//        registerUserToEntity(userService.readAllUsersFromFile());
//        registerCategoryToEntity(categoryService.readAllCategoriesFromFile());
//        registerProductToEntity(productService.readAllProductsFromFile());
//        _01_getProductsInRange(new BigDecimal("500"), new BigDecimal("1000"));
//        _02_getAllUsersSoldProducts();
//        _03_getAllCategoriesByProductsCount();
        _04_usersAndProducts();
    }

    private void _04_usersAndProducts() throws IOException {
        String json = this.userService.getUsersAndProducts();
        System.out.println(writeToFile(json, FilesPaths.USERS_AND_PRODUCTS_PATH));
    }
    private void _03_getAllCategoriesByProductsCount() throws IOException {
        String json = this.categoryService.getCategoriesByProductsCount();
        System.out.println(writeToFile(json, FilesPaths.CATEGORIES_BY_PRODUCTS_PATH));
    }
    private void _02_getAllUsersSoldProducts() throws IOException {
        String json = this.userService.successfullySoldProducts();
        System.out.println(writeToFile(json, FilesPaths.USERS_SOLD_PRODUCTS_PATH));
    }
    private void _01_getProductsInRange(BigDecimal start, BigDecimal end) throws IOException {
        String json = this.productService.productsInRange(start, end);
        System.out.println(writeToFile(json, FilesPaths.PRODUCTS_IN_RANGE_PATH));

    }
    private void registerUserToEntity(CreateUserDTO[] createUsersDTOS) {
        for (CreateUserDTO createUsersDTO : createUsersDTOS) {
            this.userService.saveUser(createUsersDTO);
        }
    }

    private void registerCategoryToEntity(CreateCategoryDTO[] createCategoriesDTOS) {
        for (CreateCategoryDTO createCategoriesDTO : createCategoriesDTOS) {
            this.categoryService.saveCategory(createCategoriesDTO);
        }
    }

    private void registerProductToEntity(CreateProductDTO[] createProductDTOS) {
        for (CreateProductDTO createProductDTO : createProductDTOS) {
            this.productService.saveProduct(createProductDTO);
        }
    }
}
