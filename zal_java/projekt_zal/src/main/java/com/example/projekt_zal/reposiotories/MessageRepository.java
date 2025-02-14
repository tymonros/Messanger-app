package com.example.projekt_zal.reposiotories;

import com.example.projekt_zal.entity.Conversation;
import com.example.projekt_zal.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.conversation = :conversation")
    Page<Message> getAllMessagesByConversation(Conversation conversation, Pageable pageable);
}
