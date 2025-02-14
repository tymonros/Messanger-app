package com.example.projekt_zal.services;

import com.example.projekt_zal.entity.User;
import com.example.projekt_zal.reposiotories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    boolean isPasswordUnique(String password){
        List<String> hashedPasswords = userRepository.findAllHashedPasswords();
        for (String hashed_password : hashedPasswords){
            if(passwordEncoder.matches(password, hashed_password)){
                return false;
            }
        }
        return true;
    }
    public String registerUser(String name, String password){
        if(userRepository.findByName(name).isPresent()){
            return "Username is already taken";
        }
        if(!isPasswordUnique(password)){
            return "Password already taken";
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(new ArrayList<>());
        newUser.getRoles().add("USER");
        userRepository.save(newUser);
        return "User successfully registered.";

    }
}
