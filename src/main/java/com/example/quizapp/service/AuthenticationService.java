package com.example.quizapp.service;


import com.example.quizapp.dao.request.SignUpRequest;
import com.example.quizapp.dao.request.SigninRequest;
import com.example.quizapp.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SigninRequest request);
}