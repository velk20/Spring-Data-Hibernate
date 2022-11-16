package com.example.gson_exercise.models.dtos.task4;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
}
