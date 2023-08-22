package com.example.quizapp.security.model;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
