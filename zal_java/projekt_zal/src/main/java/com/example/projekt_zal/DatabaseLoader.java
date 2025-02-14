package com.example.projekt_zal;

import com.example.projekt_zal.entity.User;
import com.example.projekt_zal.reposiotories.ConversationRepository;
import com.example.projekt_zal.reposiotories.MessageRepository;
import com.example.projekt_zal.reposiotories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createUsers();

    }

    public DatabaseLoader(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private void createUsers(){
        User user1 = new User();
        user1.setName("Chuj");

        user1.setPassword(passwordEncoder.encode("paSSword"));
        user1.setRoles(new ArrayList<>());
        user1.getRoles().add("USER");
        userRepository.save(user1);
        User user2 = new User();
        user2.setName("Kurwa");
        user2.setPassword(passwordEncoder.encode("kastet"));
        user2.setRoles(new ArrayList<>());
        user2.getRoles().add("USER");
        userRepository.save(user2);

    }
}
