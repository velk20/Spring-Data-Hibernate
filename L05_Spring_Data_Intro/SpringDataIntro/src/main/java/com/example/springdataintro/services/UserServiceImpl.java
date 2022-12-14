package com.example.springdataintro.services;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.models.User;
import com.example.springdataintro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, int age) {
        //Validate
        if (username.isBlank() || age < 18) {
            throw new RuntimeException("Validation failed!");
        }
        //check username is unique
        Optional<User> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            throw new RuntimeException("Username already in use");
        }
        //add default account
        Account account = new Account();
        User user = new User(username, age, account);
        //save user
        this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String first) {
        return this.userRepository.findByUsername(first).get();
    }
}
