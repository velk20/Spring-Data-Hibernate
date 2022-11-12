package com.example.modelmapperexercise.validators;

public abstract class UserValidator {
    public static boolean validatePassword(String password) {
        if (password.length() < 6) {
            return false;
        }
        if (password.chars().noneMatch(Character::isUpperCase)) {
            return false;
        }
        if (password.chars().noneMatch(Character::isLowerCase)) {
            return false;
        }
        return password.chars().anyMatch(Character::isDigit);
    }

    public static boolean validateEmail(String email) {
        if (!email.contains("@")) {
            return false;
        }
        return email.contains(".");
    }
}
