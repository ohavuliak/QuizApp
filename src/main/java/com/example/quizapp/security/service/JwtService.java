package com.example.quizapp.security.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    Key getSigningKey();
}