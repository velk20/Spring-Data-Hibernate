package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.LabelRepository;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class ShampooServiceImpl implements ShampooService {
    private final LabelRepository labelRepository;
    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository,LabelRepository labelRepository) {
        this.shampooRepository = shampooRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public List<Shampoo> findByBrandLike(String brand) {
        return this.shampooRepository.findByBrandLike(brand);
    }

    @Override
    public List<Shampoo> findByIngredient(Set<String> i) {
        return this.shampooRepository.findByIngredient(i);
    }

    @Override
    public List<Shampoo> findAllBySize(String size) {
        return this.shampooRepository.findAllBySize(Size.valueOf(size.toUpperCase()));
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabelOrderByPrice(String size, Long labelId) {
        return this.shampooRepository.findAllBySizeOrLabelOrderByPrice(Size.valueOf(size.toUpperCase()),
                this.labelRepository.findById(labelId).get());
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal bigDecimal) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(bigDecimal);
    }

    @Override
    public Long countDistinctByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countDistinctByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findAllByIngredientsCountLessThan(int count) {
        return this.shampooRepository.findAllByIngredientsCountLessThan(count);
    }

}
