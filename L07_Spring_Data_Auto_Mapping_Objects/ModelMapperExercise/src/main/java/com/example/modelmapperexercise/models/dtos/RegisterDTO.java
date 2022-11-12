package com.example.modelmapperexercise.models.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
}
