package com.example.projekt_zal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Positive
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "User_Conversation",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;
    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    public Long getId() {
        return id;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Conversation(Long id, List<User> participants, List<Message> messages) {
        this.id = id;
        this.participants = participants;
        this.messages = messages;
    }
    public Conversation (){

    }
    public String getOtherParticipantName(String currentUsername) {
        return participants.stream()
                .filter(user -> !user.getName().equals(currentUsername))
                .map(User::getName)
                .findFirst()
                .orElse("Unknown");
    }

}
