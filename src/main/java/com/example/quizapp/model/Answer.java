package com.example.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    private String username;
    private Long questionId;
    private String userAnswer;
    private boolean checker;
}
