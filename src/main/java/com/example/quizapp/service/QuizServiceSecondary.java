package com.example.quizapp.service;

import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Qualifier("secondary")
public class QuizServiceSecondary implements QuizService{
    @Override
    public String createQuiz(QuizRequest request) {
        return null;
    }

    @Override
    public List<QuestionWrapper> getQuizQuestions(Long id) {
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return null;
    }
}
