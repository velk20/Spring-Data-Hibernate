package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameStartingWith(String name);

    List<Ingredient> findAllByNameInOrderByPrice(Set<String> ingredientSet);

    @Modifying
    @Query("delete from Ingredient as i " +
            "where i.name = :name")
    void deleteIngredientByName(String name);

    @Modifying
    @Query("update Ingredient " +
            "set price = price*1.1")
    int riseIngredientPrice();

    @Modifying
    @Query("update Ingredient as i " +
            "set i.price = :price " +
            "where i.name in :names")
    int setIngredientPrices(Set<String> names, BigDecimal price);
}
