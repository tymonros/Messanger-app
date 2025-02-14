package com.example.projekt_zal.controllers;

import com.example.projekt_zal.entity.Conversation;
import com.example.projekt_zal.entity.Message;
import com.example.projekt_zal.entity.User;
import com.example.projekt_zal.reposiotories.ConversationRepository;
import com.example.projekt_zal.reposiotories.MessageRepository;
import com.example.projekt_zal.reposiotories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class ChatController {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public ChatController(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }
    @PostMapping("/start-conversation")
    public String openChat(@RequestParam String userName, Model model, Principal principal){
        List<Conversation> conversations = conversationRepository.findByUser1User2Names(userName, principal.getName());
        if(!conversations.isEmpty()){
            String loggedInUserName = principal.getName();
            model.addAttribute("conversation",conversations.getFirst());
            model.addAttribute("userName", userName);
            model.addAttribute("loggedInUser", loggedInUserName);
            return "chat";
        }
        else{
            User loggedInUser = userRepository.findByName(principal.getName()).orElseThrow(() -> new RuntimeException());
            User otherUser = userRepository.findByName(userName).orElseThrow(() -> new RuntimeException());
            Conversation newConversation = new Conversation();
            newConversation.setParticipants(Arrays.asList(loggedInUser, otherUser));
            conversationRepository.save(newConversation);
            model.addAttribute("conversation", newConversation);
            model.addAttribute("userName", userName);
            model.addAttribute("loggedInUser", loggedInUser.getName());
            return "chat";


        }



    }
    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String messageContent, @RequestParam Long conversationId, @RequestParam String senderName){

        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(()-> new RuntimeException("Conversation with ID " + conversationId + " not found"));
        Message message = new Message(conversation, senderName, messageContent);
        messageRepository.save(message);
        return "redirect:/chat?conversationId=" + conversationId;  //po to żeby doładował wiadomosci z bazy samo chat nie dziala bo zwarca z tym samym model data co wczesniej

    }
    @GetMapping("/chat")
    public String showChat(@RequestParam Long conversationId, Model model, Principal principal){
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new RuntimeException());
        String loggedInUser = principal.getName();
        String chatPartner = conversation.getParticipants().stream().map(User::getName)
                .filter(name -> !loggedInUser.equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("Chat partner not found"));
        model.addAttribute("conversation", conversation);
        model.addAttribute("userName", chatPartner);
        model.addAttribute("loggedInUser", loggedInUser);
        return "chat";

    }
    @GetMapping("/messages")
    public String getMessages(@RequestParam Long conversationId, Model model, Principal principal) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        model.addAttribute("conversation", conversation);
        model.addAttribute("loggedInUser", principal.getName());
        return "fragments/chat-messages :: messagesList";
    }
    @GetMapping("/conversations")
    public String usersConversation(Model model, Principal principal) { //Principal hold info about currently logged user

        List<Conversation> conversations = conversationRepository.findByUser1(principal.getName());
        for (Conversation conversation : conversations) {
            if (conversation.getParticipants() != null) {
                conversation.getParticipants().removeIf(Objects::isNull);
            }
        }

        model.addAttribute("conversations",conversations);
        model.addAttribute("name",principal.getName());
        return "conversations"; // Renders home.html
    }


}
