package com.example.projekt_zal.controllers;

import com.example.projekt_zal.services.LoginService;
import com.example.projekt_zal.entity.User;
import com.example.projekt_zal.reposiotories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    private final UserRepository userRepository;
    private final LoginService loginService;


    public LoginController(UserRepository userRepository, LoginService loginService) {
        this.userRepository = userRepository;
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public String login(){return "login";}
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration"; // This must match `registration.html`
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (user.getPassword() == null || user.getPassword().length() < 2 || user.getPassword().length() > 20) {
            bindingResult.rejectValue("password", "error.password", "Blad w nazwie");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            model.addAttribute("user", user);
            return "registration";
        }

        try {
            String result = loginService.registerUser(user.getName(), user.getPassword());

            if (result.equals("User successfully registered.")) {
                return "redirect:/login"; // Redirect on success
            } else {
                model.addAttribute("error", result); // Add error message to model
                model.addAttribute("user", new User()); // Ensure 'user' is present
                return "registration"; // Reload the form
            }
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            model.addAttribute("user", new User());
            return "registration"; // Stay on the same page with an error
        }
    }

}
