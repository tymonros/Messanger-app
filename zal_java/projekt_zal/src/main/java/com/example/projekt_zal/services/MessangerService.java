package com.example.projekt_zal.services;

import com.example.projekt_zal.entity.Conversation;
import com.example.projekt_zal.entity.Message;
import com.example.projekt_zal.reposiotories.ConversationRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MessangerService {
    private final ConversationRepository conversationRepository;

    public MessangerService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }
    public List<Conversation> getLastThreeConversations(Principal principal){

        List<Conversation> userConversations = conversationRepository.findByUser1(principal.getName());
        return userConversations.stream().sorted((c1, c2) ->{
            List<Message> messages1 = c1.getMessages();
            List<Message> messages2 = c2.getMessages();
            // Get the last message from each conversation, if it exists
            LocalDateTime lastMessage1 = messages1.isEmpty() ? null : messages1.getLast().getTimestamp();
            LocalDateTime lastMessage2 = messages2.isEmpty() ? null : messages2.getLast().getTimestamp();
            // Compare timestamps, handling cases where one or both might be null
            if (lastMessage1 == null) return (lastMessage2 == null) ? 0 : 1;
            if (lastMessage2 == null) return -1;
            return lastMessage2.compareTo(lastMessage1); // LocalDateTime implements Comparable
        }).limit(3).toList();

    }
}
