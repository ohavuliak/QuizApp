package com.example.quizapp.service;

import com.example.quizapp.exception.QuizNotFoundException;
import com.example.quizapp.model.*;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;

    public String createQuiz(QuizRequest request) {
        List<Question> questions = questionRepository
                .findRandomQuestionsByCategory(request.getCategory(), request.getNumQ());

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return "Quiz was successfully created.";
    }


    public List<QuestionWrapper> getQuizQuestions(Long id) {
        Optional<Quiz> quiz = Optional.ofNullable(quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz with ID " + id + " not found.")));
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return questionsForUser;
    }
}