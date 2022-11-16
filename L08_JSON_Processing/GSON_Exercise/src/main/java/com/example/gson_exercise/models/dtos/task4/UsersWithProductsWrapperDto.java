package com.example.gson_exercise.models.dtos.task4;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UsersWithProductsWrapperDto {
    @Expose
    private int usersCount;
    @Expose
    private List<UserWithProductsDto> users;

    public UsersWithProductsWrapperDto(List<UserWithProductsDto> users) {
        this.users = users;
        this.usersCount = users.size();
    }

}
