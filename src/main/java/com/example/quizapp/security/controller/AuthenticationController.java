package com.example.quizapp.security.controller;


import com.example.quizapp.security.dao.request.SignUpRequest;
import com.example.quizapp.security.dao.request.SigninRequest;
import com.example.quizapp.security.dao.response.JwtAuthenticationResponse;
import com.example.quizapp.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name="Authentication", description = "Sign in and sign up user")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    @Operation(summary = "Sign up new user")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @Operation(summary = "Sign in existing user")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
