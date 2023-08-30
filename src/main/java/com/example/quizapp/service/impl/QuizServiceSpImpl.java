package com.example.quizapp.service.impl;

import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.dto.FeedbackDTO;
import com.example.quizapp.dto.UserAnswerDTO;
import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.mapper.QuizMapper;
import com.example.quizapp.model.*;
import com.example.quizapp.repository.FeedbackRepository;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Qualifier("withSpecifications")
public class QuizServiceSpImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuizMapper quizMapper;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
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
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);

        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            List<Question> questions = quiz.getQuestions();

            int correctAnswers = 0;

            for (UserAnswerDTO userAnswer : userAnswers) {
                Optional<Question> optionalQuestion = questions.stream()
                        .filter(question -> question.getId().equals(userAnswer.getQuestionId()))
                        .findFirst();

                if (optionalQuestion.isPresent()) {
                    Question question = optionalQuestion.get();
                    if (question.getRightAnswer().equals(userAnswer.getUserAnswer())) {
                        correctAnswers++;
                    }
                }
            }

            return correctAnswers + "/" + questions.size();
        } else {
            throw new NotFoundException(MessageCode.NOT_FOUND_QUIZ);
        }
    }

    @Override
    @Transactional
    public Quiz createQuizWithQuestions(Quiz quiz) {
        quizRepository.save(quiz);
        questionRepository.saveAll(quiz.getQuestions());
        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("The quiz must have at least one question.");
        }
        return quiz;
    }
}
