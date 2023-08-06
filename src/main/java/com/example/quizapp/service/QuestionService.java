package com.example.quizapp.service;

import com.example.quizapp.exception.QuestionNotFoundException;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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
        return questionRepository.findByCategory(category);
    }

    public String addQuestion(Question question){
        questionRepository.save(question);
        return "Question was successfully added.";
    }

    public String deleteQuestion(Integer id) {
        List<Quiz> quizzes = quizRepository.findByQuestionsId(id);
        for(Quiz quiz: quizzes){
            quiz.getQuestions().removeIf(q -> q.getId()==id);
        }
        questionRepository.deleteById(id);
        return "Question was successfully deleted.";
    }

    public String updateQuestion(Question question, Integer id) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with ID " + id + " not found."));

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
}