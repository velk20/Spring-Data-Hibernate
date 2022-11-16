package com.example.gson_exercise.models.dtos;

import com.example.gson_exercise.models.entities.Product;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsersSoldProductsDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<SoldProductDTO> soldProducts;

    public UsersSoldProductsDTO(String firstName, String lastName, Set<SoldProductDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }
}
