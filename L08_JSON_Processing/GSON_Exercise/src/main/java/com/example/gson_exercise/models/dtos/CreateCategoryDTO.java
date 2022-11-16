package com.example.gson_exercise.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
    @Expose
    private String name;
}
