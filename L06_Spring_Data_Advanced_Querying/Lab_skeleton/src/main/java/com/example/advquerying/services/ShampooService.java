package com.example.advquerying.services;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public interface ShampooService {
    List<Shampoo> findByBrandLike(String brand);

    List<Shampoo> findByIngredient(Set<String> i);

    List<Shampoo> findAllBySize(String size);

    List<Shampoo> findAllBySizeOrLabelOrderByPrice(String size, Long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal bigDecimal);

    Long countDistinctByPriceLessThan(BigDecimal price);

    List<Shampoo> findAllByIngredientsCountLessThan(int count);

}
