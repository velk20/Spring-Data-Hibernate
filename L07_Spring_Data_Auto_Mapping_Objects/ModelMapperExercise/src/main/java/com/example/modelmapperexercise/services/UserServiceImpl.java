package com.example.modelmapperexercise.services;

import com.example.modelmapperexercise.models.dtos.LoginDTO;
import com.example.modelmapperexercise.models.dtos.RegisterDTO;
import com.example.modelmapperexercise.models.entities.User;
import com.example.modelmapperexercise.repositories.UserRepository;
import com.example.modelmapperexercise.validators.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{
    public User currentUser;
    private final UserRepository userRepository;
    private final ModelMapper mapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User getLoggedUser() {
        return this.currentUser;
    }

    @Override
    public User registerUser(RegisterDTO registerDTO) throws IllegalAccessException {
        isAlreadyLoggedUser();


        if (!UserValidator.validateEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email!");
        }
        if (!UserValidator.validatePassword(registerDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid password!");
        }
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        User user = mapper.map(registerDTO, User.class);
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        return this.userRepository.save(user);
    }

    @Override
    public User loginUser(LoginDTO loginDTO) throws IllegalAccessException {
        isAlreadyLoggedUser();

        User user = this.userRepository.findByEmail(loginDTO.getEmail());
        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new IllegalArgumentException("Wrong user email or password");
        }
        this.currentUser = user;
        return user;
    }

    @Override
    public String logout() {
        if (!isUserLogged()) {
            throw new IllegalArgumentException("No logged user!\nPLease first log in!");
        }
        String nameUser = this.currentUser.getFullName();
        this.currentUser = null;
        return nameUser;
    }

    private boolean isUserLogged(){
        return currentUser != null;
    }
    private void isAlreadyLoggedUser() throws IllegalAccessException {
        if (isUserLogged()) {
            throw new IllegalAccessException("You are already logged in");
        }
    }
}
