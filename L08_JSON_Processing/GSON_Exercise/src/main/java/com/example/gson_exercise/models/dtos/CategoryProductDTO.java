package com.example.gson_exercise.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CategoryProductDTO {
    @Expose
    private String name;
    @Expose
    private long productsCount;
    @Expose
    private double averagePrice;
    @Expose
    private BigDecimal totalRevenue;

    public CategoryProductDTO(String name, long productsCount, double averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }
}
