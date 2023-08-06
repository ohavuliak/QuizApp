package com.example.quizapp.service;

import com.example.quizapp.exception.MessageCode;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuizRepository quizRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return  questionRepository.findByCategory(category);
    }

    public Question addQuestion(Question question){
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        List<Quiz> quizzes = quizRepository.findByQuestionsId(id);
        for(Quiz quiz: quizzes){
            quiz.getQuestions().removeIf(q -> q.getId().equals(id));
        }
        questionRepository.deleteById(id);
    }

    public String updateQuestion(Question question, Long id) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUESTION));
        existingQuestion.setQuestionTitle(question.getQuestionTitle());
        existingQuestion.setCategory(question.getCategory());
        existingQuestion.setDifficultylevel(question.getDifficultylevel());
        existingQuestion.setOption1(question.getOption1());
        existingQuestion.setOption2(question.getOption2());
        existingQuestion.setOption3(question.getOption3());
        existingQuestion.setOption4(question.getOption4());
        existingQuestion.setRightAnswer(question.getRightAnswer());

        questionRepository.save(existingQuestion);

        return "Question was successfully updated";
    }

    public Boolean checkAnswer(Long id, Integer option) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND_QUESTION));

        List<String> options = List.of(question.getOption1(), question.getOption2(),
                question.getOption3(), question.getOption4());

        if (option < 1 || option > options.size())
            throw new NotFoundException(MessageCode.NOT_FOUND_OPTION);

        String userOption = options.get(option-1);
        return userOption.equals(question.getRightAnswer());
    }

    public Map<String, Object> getPageableQuestions(final Integer pageNumber, Integer size) {
        return convertToResponse(questionRepository.getQuestions(PageRequest.of(pageNumber, size)));
    }
    private Map<String, Object> convertToResponse(final Page<Question> questions) {
        Map<String, Object> response = new HashMap<>();
        response.put("questions", questions.getContent());
        response.put("current-page", questions.getNumber());
        response.put("total-items", questions.getTotalElements());
        response.put("total-pages", questions.getTotalPages());
        return response;
    }
}
