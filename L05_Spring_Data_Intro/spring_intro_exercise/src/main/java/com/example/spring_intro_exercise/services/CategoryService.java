package com.example.spring_intro_exercise.services;

import com.example.spring_intro_exercise.models.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
