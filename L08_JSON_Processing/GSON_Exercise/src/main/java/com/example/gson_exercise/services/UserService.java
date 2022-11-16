package com.example.gson_exercise.services;

import com.example.gson_exercise.models.dtos.CreateUserDTO;

import java.io.IOException;

public interface UserService {
    CreateUserDTO[] readAllUsersFromFile() throws IOException;

    void saveUser(CreateUserDTO createUserDTO);

    String successfullySoldProducts();

    String getUsersAndProducts();

}
