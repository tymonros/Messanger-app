package com.example.projekt_zal.services;
import com.example.projekt_zal.entity.User;
import com.example.projekt_zal.reposiotories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        // No setup needed, @InjectMocks handles instantiation
    }

    @Test  //Check if password is unique when its not yet present
    void testIsPasswordUnique_UniquePassword() {
        when(userRepository.findAllHashedPasswords()).thenReturn(List.of("hashed1", "hashed2"));
        when(passwordEncoder.matches("newPassword", "hashed1")).thenReturn(false);
        when(passwordEncoder.matches("newPassword", "hashed2")).thenReturn(false);

        assertTrue(loginService.isPasswordUnique("newPassword"));
    }

    @Test // Checks if a password is not unique when its already hashed in the database.
    void testIsPasswordUnique_NonUniquePassword() {
        when(userRepository.findAllHashedPasswords()).thenReturn(List.of("hashed1", "hashed2"));
        when(passwordEncoder.matches("newPassword", "hashed1")).thenReturn(true);

        assertFalse(loginService.isPasswordUnique("newPassword"));
    }

    @Test //Checks the registration process when the username is available and the password is unique
    void testRegisterUser_SuccessfulRegistration() {
        when(userRepository.findByName("newUser")).thenReturn(Optional.empty());
        when(userRepository.findAllHashedPasswords()).thenReturn(List.of());
        when(passwordEncoder.encode("securePassword")).thenReturn("encodedPassword");

        String result = loginService.registerUser("newUser", "securePassword");

        assertEquals("User successfully registered.", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test  //Checks that the registration fails if the username is already taken
    void testRegisterUser_UsernameTaken() {
        when(userRepository.findByName("existingUser")).thenReturn(Optional.of(new User()));

        String result = loginService.registerUser("existingUser", "password");

        assertEquals("Username is already taken", result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test  //Checks if the registration fails when the password is not unique
    void testRegisterUser_PasswordNotUnique() {
        when(userRepository.findByName("newUser")).thenReturn(Optional.empty());
        when(userRepository.findAllHashedPasswords()).thenReturn(List.of("hashedPassword"));
        when(passwordEncoder.matches("securePassword", "hashedPassword")).thenReturn(true);

        String result = loginService.registerUser("newUser", "securePassword");

        assertEquals("Password already taken", result);
        verify(userRepository, never()).save(any(User.class));
    }
}

