package com.example.spring_intro_exercise.services;

import com.example.spring_intro_exercise.models.Category;
import com.example.spring_intro_exercise.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getRandomCategories() {
        long count = this.categoryRepository.count();
        long categoriesCount = new Random().nextLong(count)+1;

        Set<Long> categoriesIds = new HashSet<>();
        for (int i = 0; i < categoriesCount; i++) {
            long nextId = new Random().nextLong(count) + 1;

            categoriesIds.add(nextId);
        }

        List<Category> allById = this.categoryRepository.findAllById(categoriesIds);

        return new HashSet<>(allById);
    }
}
