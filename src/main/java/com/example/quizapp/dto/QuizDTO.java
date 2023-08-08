package com.example.quizapp.dto;

import com.example.quizapp.model.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuizDTO {
    private Long id;
    private String title;
    private List<Question> questions;
}
