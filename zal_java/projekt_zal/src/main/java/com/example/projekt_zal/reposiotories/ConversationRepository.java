package com.example.projekt_zal.reposiotories;

import com.example.projekt_zal.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c JOIN c.participants first JOIN c.participants second "+
    "WHERE (first.name= :user1 AND second.name = :user2) OR (first.name = :user2 AND second.name = :user1)"
    )
    List<Conversation> findByUser1User2Names(String user1, String user2);

    @Query("SELECT c FROM Conversation c JOIN c.participants first JOIN c.participants second "+
            "WHERE (first.name= :user1 ) OR (second.name = :user1)"
    )
    List<Conversation> findByUser1(String user1);

}
