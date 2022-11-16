package com.example.gson_exercise.services;

import com.example.gson_exercise.models.dtos.CreateCategoryDTO;
import com.example.gson_exercise.models.dtos.CreateUserDTO;
import com.example.gson_exercise.models.entities.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    CreateCategoryDTO[] readAllCategoriesFromFile() throws IOException;

    String getCategoriesByProductsCount();

    void saveCategory(CreateCategoryDTO createCategoryDTO);
}
