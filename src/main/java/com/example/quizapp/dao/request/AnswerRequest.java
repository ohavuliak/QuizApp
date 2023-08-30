package com.example.quizapp.dao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {
    private String token;
    private Long questionId;
    private String userAnswer;
}
