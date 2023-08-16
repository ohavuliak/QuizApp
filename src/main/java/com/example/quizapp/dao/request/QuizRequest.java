package com.example.quizapp.dao.request;

import lombok.Data;

@Data
public class QuizRequest {
    private String category;
    private Integer numQ;
    private String title;
    private String difficultylevel;
}
