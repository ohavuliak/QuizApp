package com.example.quizapp.service;

import com.example.quizapp.model.*;
import com.example.quizapp.dao.request.QuizRequest;

import java.util.List;


public interface QuizService {
    String createQuiz(QuizRequest request) ;
    List<QuestionWrapper> getQuizQuestions(Long id) ;
    List<Quiz> getAllQuizzes() ;
}