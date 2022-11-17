package com.example.xml_exercise;

import com.example.xml_exercise.services.CategoryService;
import com.example.xml_exercise.services.ProductService;
import com.example.xml_exercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedReader;
import java.math.BigDecimal;

@Component
public class Main implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public Main(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
//        _01_seedDB();
//        _Q1_ProductsInRange();
//        _Q2_SuccessfullySoldProducts();
//        _Q3_CategoriesByProductsCount();
        _Q4_UsersAndProducts();
    }

    private void _Q4_UsersAndProducts() throws JAXBException {
        this.userService.usersAndProducts();
    }

    private void _Q3_CategoriesByProductsCount() throws JAXBException {
        this.categoryService.getAllCategoriesByProductsCount();
    }

    private void _Q2_SuccessfullySoldProducts() throws JAXBException {
        this.userService.successfullySoldProducts();
    }

    private void _Q1_ProductsInRange() throws Exception {
        this.productService.productsInRange(new BigDecimal("500"), new BigDecimal("1000"));
    }

    private void _01_seedDB() throws JAXBException, IOException {
        userService.seedUsers();
        categoryService.seedCategories();
        productService.seedProducts();
    }
}
