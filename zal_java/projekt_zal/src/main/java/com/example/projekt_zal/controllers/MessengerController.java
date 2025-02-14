package com.example.projekt_zal.controllers;

import com.example.projekt_zal.services.MessangerService;
import com.example.projekt_zal.entity.Conversation;
import com.example.projekt_zal.entity.User;
import com.example.projekt_zal.reposiotories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class MessengerController {
    private final UserRepository userRepository;
    private final MessangerService messangerService;

    public MessengerController(UserRepository userRepository, MessangerService messangerService) {
        this.userRepository = userRepository;
        this.messangerService = messangerService;
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) { //Principal hold info about currently logged user

        model.addAttribute("name", principal.getName()); // Get the logged-in username

        List<Conversation> lastThreeConversations = messangerService.getLastThreeConversations(principal);
        model.addAttribute("lastThreeConversations", lastThreeConversations);
        return "homepage"; // Renders home.html
    }
    @GetMapping("/search")
    public String searchUsers(@RequestParam String query, Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("name", principal.getName()); // Get the logged-in username
        } else {
            model.addAttribute("name", "Guest");
        }
        List<User> users = userRepository.findByNameContainingIgnoreCase(query);
        model.addAttribute("users", users);
        return "home"; // Reload home.html with search results
    }
    @GetMapping("/new-conversation")
    public String searchPage(Model model, Principal principal) { //Principal hold info about currently logged user
        if (principal != null) {
            model.addAttribute("name", principal.getName()); // Get the logged-in username
        } else {
            model.addAttribute("name", "Guest");
        }
        return "home"; // Renders home.html
    }

}
