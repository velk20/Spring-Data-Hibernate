package com.example.modelmapperexercise.services;

import com.example.modelmapperexercise.models.dtos.LoginDTO;
import com.example.modelmapperexercise.models.dtos.RegisterDTO;
import com.example.modelmapperexercise.models.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getLoggedUser();
    User registerUser(RegisterDTO registerDTO) throws IllegalAccessException;

    User loginUser(LoginDTO loginDTO) throws IllegalAccessException;

    String logout();
}
