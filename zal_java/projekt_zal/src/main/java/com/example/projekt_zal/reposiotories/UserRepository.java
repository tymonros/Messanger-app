package com.example.projekt_zal.reposiotories;

import com.example.projekt_zal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    @Query("SELECT u.password FROM User u")
    List<String> findAllHashedPasswords();
    List<User> findByNameContainingIgnoreCase(String name);

}
