package com.example.projekt_zal.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id

    @Positive
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 20, message = "{user.name.size}")
    private String name;
    //@Size(min = 2, max = 20, message = "{user.password1}")
    private String password;
    private List<String> roles;
    @ManyToMany(mappedBy = "participants")
    private List<Conversation> conversation;

    public User(Long id, String name, String password, List<String> roles, List<Conversation> conversation) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.conversation = conversation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<Conversation> getConversation() {
        return conversation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setConversation(List<Conversation> conversation) {
        this.conversation = conversation;
    }
    public User (){

    }

}
