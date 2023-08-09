package com.example.quizapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService(); //for loadUserByUsername() - supply user information to Spring Security API
}