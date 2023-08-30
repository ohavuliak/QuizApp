package com.example.quizapp.service;

import com.example.quizapp.dao.request.FeedbackRequest;
import com.example.quizapp.dto.FeedbackDTO;
import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.model.Feedback;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.FeedbackRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final FeedbackRepository feedbackRepository;
    public Feedback addFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        feedback.setUser(userRepository.findUserById(feedbackRequest.getUserId()));
        feedback.setQuiz(quizRepository.findById(feedbackRequest.getQuizId())
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUIZ)));
        feedback.setFeedbackText(feedbackRequest.getFeedbackText());
        feedback.setRating(feedbackRequest.getRating());
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Transactional(readOnly = true)
    public List<Feedback> getQuizFeedback(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(
                ()->new NotFoundException(MessageCode.NOT_FOUND_QUIZ)
        );
        return feedbackRepository.findByQuizId(quiz);
    }
}
