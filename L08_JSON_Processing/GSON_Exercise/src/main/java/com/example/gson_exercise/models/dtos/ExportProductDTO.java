package com.example.gson_exercise.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class ExportProductDTO{
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String sellerFullName;

    public ExportProductDTO(String name, BigDecimal price, String sellerFullName) {
        this.name = name;
        this.price = price;
        this.sellerFullName = sellerFullName;
    }
}
