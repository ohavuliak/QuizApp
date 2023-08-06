package com.example.quizapp.service;

import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.model.*;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



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
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUIZ));
        List<Question> questionsFromDB = quiz.getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return questionsForUser;
    }
}