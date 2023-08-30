package com.example.quizapp.dto;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {
    private Long id;
    private User user;
    private Quiz quiz;
    private String feedbackText;
    private int rating;
}
