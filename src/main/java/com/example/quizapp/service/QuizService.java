package com.example.quizapp.service;

import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.*;
import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

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
        return questionMapper.questionToQuestionWrapper(quiz.getQuestions());
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
}