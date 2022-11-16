package com.example.gson_exercise.models.dtos;

import com.example.gson_exercise.models.entities.Category;
import com.example.gson_exercise.models.entities.User;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private User buyer;
    @Expose
    private User seller;
    @Expose
    private Set<Category> categories;


}
