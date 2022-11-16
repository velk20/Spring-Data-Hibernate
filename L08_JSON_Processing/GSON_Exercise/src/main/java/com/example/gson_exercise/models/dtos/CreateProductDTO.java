package com.example.gson_exercise.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
}
