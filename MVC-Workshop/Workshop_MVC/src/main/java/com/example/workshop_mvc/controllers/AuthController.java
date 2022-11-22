package com.example.workshop_mvc.controllers;

import com.example.workshop_mvc.models.dtos.RegistrationDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    @GetMapping("users/register")
    public String registerView() {
        return "user/register";
    }

    @PostMapping(value = "users/register",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String doRegister(@RequestBody RegistrationDTO data) {
        System.out.println(data.toString());
        return "user/register";
    }
}
