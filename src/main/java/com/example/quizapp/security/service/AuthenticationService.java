package com.example.quizapp.security.service;


import com.example.quizapp.security.dao.request.SignUpRequest;
import com.example.quizapp.security.dao.request.SigninRequest;
import com.example.quizapp.security.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SigninRequest request);
}