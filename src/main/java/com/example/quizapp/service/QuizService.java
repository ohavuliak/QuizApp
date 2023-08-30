package com.example.quizapp.service;

import com.example.quizapp.dto.UserAnswerDTO;
import com.example.quizapp.model.*;
import com.example.quizapp.dao.request.QuizRequest;

import java.util.List;


public interface QuizService {
    Quiz createQuiz(QuizRequest request) ;
    List<QuestionWrapper> getQuizQuestions(Long id) ;
    List<Quiz> getAllQuizzes() ;
    void deleteQuiz(Long id);
    String updateQuiz(Quiz updatedQuiz, Long id);
    String submitQuizAnswers(Long id, List<UserAnswerDTO> userAnswers);
    Quiz createQuizWithQuestions(Quiz quiz);
}