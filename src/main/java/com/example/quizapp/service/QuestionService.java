package com.example.quizapp.service;



import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;

import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.specifications.QuestionSpecifications;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;


@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;
    @Value("${updateMessage}")
    private String updateMessage;


    public List<Question> getAllQuestions(String category) {
        log.debug("Inside getAllQuestion()");
        return category==null? questionRepository.findAll()
                : questionRepository.findByCategory(category);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 30, rollbackFor = Exception.class)
    public Question addQuestion(Question question){
        log.debug("Inside addQuestion()");
        return questionRepository.save(question);
    }
    public void deleteQuestion(Long id) {
        List<Quiz> quizzes = quizRepository.findByQuestionsId(id);
        log.debug("Inside deleteQuestion()");
        for(Quiz quiz: quizzes){
            quiz.getQuestions().removeIf(q -> q.getId().equals(id));
        }
        questionRepository.deleteById(id);
    }

    @Transactional
    public String updateQuestion(Question question, Long id) {
        log.debug("Inside updateQuestion()");
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUESTION));
        questionMapper.updateEntity(existingQuestion, question);
        questionRepository.save(existingQuestion);
        return updateMessage;
    }

    @Transactional(readOnly = true)
    public Page<Question> getCategoryPageable(String category, Pageable pageable) {
        log.debug("Inside getCategoryPageable()");
        return category == null ? questionRepository.findAll(pageable) : questionRepository.findByCategory(category, pageable);
    }
    @Transactional(readOnly = true)
    public Question getQuestionById(Long id) {
        log.debug("Inside getQuestionById()");
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUESTION));
    }
}
