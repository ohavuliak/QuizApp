package com.example.quizapp.repository;

import com.example.quizapp.model.Answer;

public interface AnswerRepositoryCustom {
    void customSave(Answer answer);
    Integer countCorrectAnswers(String username);
    Integer countIncorrectAnswers(String username);
    Answer findAnswerById(Long id);
    void deleteAnswerById(Long id);
}
