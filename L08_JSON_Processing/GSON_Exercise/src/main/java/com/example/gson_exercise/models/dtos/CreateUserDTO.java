package com.example.gson_exercise.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
}
