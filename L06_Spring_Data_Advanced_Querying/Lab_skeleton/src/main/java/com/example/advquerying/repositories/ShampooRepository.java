package com.example.advquerying.repositories;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal bigDecimal);
    List<Shampoo> findAllBySizeOrLabelOrderByPrice(Size size, Label label);
    List<Shampoo> findAllBySize(Size size);
    List<Shampoo> findByBrandLike(String brand);

    Long countDistinctByPriceLessThan(BigDecimal price);

    @Query("select s from Shampoo as s " +
            "join s.ingredients as i " +
            "where i.name in :names")
    List<Shampoo> findByIngredient(@Param(value = "names") Set<String> ingredientNames);

    @Query("select s from Shampoo as s " +
            "where s.ingredients.size < :count")
    List<Shampoo> findAllByIngredientsCountLessThan(int count);

}
