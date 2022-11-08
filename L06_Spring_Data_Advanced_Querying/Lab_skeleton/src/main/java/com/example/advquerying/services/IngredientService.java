package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public interface IngredientService {
    List<Ingredient> findAllByNameStartingWith(String name);
    List<Ingredient> findAllByNameIn(Set<String> ingredientSetNames);
    void deleteIngredientByName(String name);
    int riseIngredientPrice();
    int setIngredientPrices(Set<String> names, BigDecimal price);

}
