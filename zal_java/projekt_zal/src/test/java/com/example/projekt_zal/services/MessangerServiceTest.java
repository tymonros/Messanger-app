package com.example.projekt_zal.services;

import com.example.projekt_zal.entity.Conversation;
import com.example.projekt_zal.entity.Message;
import com.example.projekt_zal.reposiotories.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MessangerServiceTest {

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private Principal principal;

    private MessangerService messangerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messangerService = new MessangerService(conversationRepository);
    }

    @Test
    void testGetLastThreeConversations() {
        // Prepare mock data
        List<Conversation> conversations = new ArrayList<>();

        // Create a sample conversation
        Conversation conversation1 = new Conversation();
        Message message1 = new Message();
        message1.setTimestamp(LocalDateTime.of(2025, 2, 13, 12, 0, 0));
        conversation1.setMessages(List.of(message1));

        Conversation conversation2 = new Conversation();
        Message message2 = new Message();
        message2.setTimestamp(LocalDateTime.of(2025, 2, 13, 14, 0, 0));
        conversation2.setMessages(List.of(message2));

        Conversation conversation3 = new Conversation();
        Message message3 = new Message();
        message3.setTimestamp(LocalDateTime.of(2025, 2, 13, 13, 0, 0));
        conversation3.setMessages(List.of(message3));

        conversations.add(conversation1);
        conversations.add(conversation2);
        conversations.add(conversation3);

        // Mock the behavior of the conversationRepository
        when(principal.getName()).thenReturn("user1");
        when(conversationRepository.findByUser1("user1")).thenReturn(conversations);

        // Call the method
        List<Conversation> result = messangerService.getLastThreeConversations(principal);

        // Verify the behavior and assertions
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(conversation2, result.get(0)); // Most recent conversation
        assertEquals(conversation3, result.get(1));
        assertEquals(conversation1, result.get(2));

        // Verify interactions with mocked objects
        verify(conversationRepository, times(1)).findByUser1("user1");
    }


}

