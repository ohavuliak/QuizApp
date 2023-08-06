package com.example.quizapp.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class QuizRequest {
    private String category;
    private Integer numQ;
    private String title;
}
