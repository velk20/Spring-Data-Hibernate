package com.example.gson_exercise.services;

import com.example.gson_exercise.models.dtos.CategoryProductDTO;
import com.example.gson_exercise.models.dtos.CreateCategoryDTO;
import com.example.gson_exercise.models.entities.Category;
import com.example.gson_exercise.models.utils.FilesPaths;
import com.example.gson_exercise.repositories.CategoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static com.example.gson_exercise.models.utils.ReadJsonFromFile.readFromFile;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public CreateCategoryDTO[] readAllCategoriesFromFile() throws IOException {
        String json = readFromFile(FilesPaths.CATEGORIES_FILE_PATH);
        return gson.fromJson(json, CreateCategoryDTO[].class);
    }

    @Override
    @Transactional
    public String getCategoriesByProductsCount() {
        List<CategoryProductDTO> categoriesByProductsCount = this.categoryRepository.getCategoriesByProductsCount();
        Gson outputGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        return outputGson.toJson(categoriesByProductsCount);
    }

    @Override
    public void saveCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = mapper.map(createCategoryDTO, Category.class);
        this.categoryRepository.save(category);
    }
}
