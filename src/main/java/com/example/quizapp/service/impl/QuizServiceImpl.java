package com.example.quizapp.service.impl;

import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.dto.FeedbackDTO;
import com.example.quizapp.dto.UserAnswerDTO;
import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.mapper.QuizMapper;
import com.example.quizapp.model.Feedback;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Primary
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuizMapper quizMapper;
    @Override
    public Quiz createQuiz(QuizRequest request) {
        List<Question> questions = questionRepository
                .findRandomQuestionsByCategoryAndDifficultylevel(request.getCategory(), request.getNumQ(), request.getDifficultylevel());

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return quiz;
    }

    @Override
    public List<QuestionWrapper> getQuizQuestions(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUIZ));
        return questionMapper.questionToQuestionWrapper(quiz.getQuestions());
    }
    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public String updateQuiz(Quiz updatedQuiz, Long id) {
        Quiz existingQuiz = quizRepository.findById(id).orElseThrow( () -> new NotFoundException(MessageCode.NOT_FOUND_QUIZ));
        quizMapper.updateEntity(existingQuiz, updatedQuiz);
        quizRepository.save(existingQuiz);
        return "Quiz was successfully updated";
    }

    @Override
    public String submitQuizAnswers(Long id, List<UserAnswerDTO> userAnswers) {
        return null;
    }

}
