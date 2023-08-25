package com.example.quizapp.dao.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private Long userId;
    private Long quizId;
    private String feedbackText;
    private int rating;
}
