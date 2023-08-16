package com.example.quizapp.service.impl;

import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.quizapp.repository.specifications.QuizSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Qualifier("withSpecifications")
public class QuizServiceSpImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    @Override
    public String createQuiz(QuizRequest request) {
        List<Question> questions = questionRepository
                .findRandomQuestionsByCategoryAndDifficultylevel(request.getCategory(), request.getNumQ(), request.getDifficultylevel());

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return "Quiz was successfully created.";
    }

    @Override
    public List<QuestionWrapper> getQuizQuestions(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUIZ));
        return questionMapper.questionToQuestionWrapper(quiz.getQuestions());
    }
    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll(where(hasAtLeastNumberOfQuestions(3).and(titleContains("Java"))));
    }
}
