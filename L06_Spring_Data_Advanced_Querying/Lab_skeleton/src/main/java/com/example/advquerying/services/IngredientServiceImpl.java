package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameStartingWith(String name) {
        return this.ingredientRepository.findAllByNameStartingWith(name);
    }

    @Override
    public List<Ingredient> findAllByNameIn(Set<String> ingredientSetNames){
        return this.ingredientRepository.findAllByNameInOrderByPrice(ingredientSetNames);
    }

    @Override
    @Transactional
    public void deleteIngredientByName(String name) {
         this.ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    @Transactional
    public int riseIngredientPrice() {
        return this.ingredientRepository.riseIngredientPrice();
    }

    @Override
    @Transactional
    public int setIngredientPrices(Set<String> names, BigDecimal price) {
        return this.ingredientRepository.setIngredientPrices(names, price);
    }

}
