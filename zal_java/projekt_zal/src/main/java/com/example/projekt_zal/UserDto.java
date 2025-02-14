package com.example.projekt_zal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotBlank(message = "Username is required")
    private String username;



    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    // Getters and setters

    // Ensure password and confirm password match
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}

