package com.example.webflix.service;

import com.example.webflix.model.UserAccount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final List<UserAccount> users = new ArrayList<>();
    private long nextUserId = 3L;

    public AuthService() {
        // Varsayılan Yönetici (Admin) Hesabı
        users.add(new UserAccount(1L, "admin", "Sistem Yöneticisi", "admin@webflix.com", "admin123", "ADMIN"));
        // Varsayılan Standart Kullanıcı Hesabı
        users.add(new UserAccount(2L, "kullanici", "Ahmet Yılmaz", "ahmet@webflix.com", "1234", "USER"));
    }

    public UserAccount authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username.trim()) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean usernameExists(String username) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username.trim()));
    }

    public UserAccount registerUser(String fullName, String username, String email, String password) {
        UserAccount newUser = new UserAccount(
                nextUserId++,
                username.trim(),
                fullName.trim(),
                email.trim(),
                password,
                "USER"
        );
        users.add(newUser);
        return newUser;
    }

    public List<UserAccount> getAllUsers() {
        return users;
    }
}