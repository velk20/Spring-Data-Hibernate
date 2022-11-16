package com.example.gson_exercise.models.dtos.task4;

import com.example.gson_exercise.models.entities.Product;
import com.example.gson_exercise.models.entities.User;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private Set<Product> soldProducts;
    @Expose
    private Set<Product> boughtProducts;
    @Expose
    private Set<User> friends;


}
