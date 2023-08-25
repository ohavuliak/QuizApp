package com.example.quizapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswerDTO {
    private Long questionId;
    private String userAnswer;
}
