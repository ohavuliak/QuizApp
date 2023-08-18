package com.example.quizapp.serviceTests;
import com.example.quizapp.exception.NotFoundException;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.DifficultyLevel;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    QuestionRepository questionRepository;
    @Mock
    QuizRepository quizRepository;
    @InjectMocks
    private QuestionService questionService;
    @Mock
    QuestionMapper questionMapper;

    private Question question;

    @BeforeEach
    public void setup(){
        question = new Question();
        question.setId(1L);
        question.setQuestionTitle("Test Question Title");
        question.setCategory("Test Category");
        question.setDifficultylevel(DifficultyLevel.EASY);
        question.setOption1("Test Option 1");
        question.setOption2("Test Option 2");
        question.setOption3("Test Option 3");
        question.setOption4("Test Option 4");
        question.setRightAnswer("Test Option 1");
    }

    @DisplayName("GET all questions test")
    @Test
    public void getAllQuestionsTest(){
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        given(questionRepository.findAll()).willReturn(questions);
        List<Question> expected = questionService.getAllQuestions(null);
        assertEquals(expected, questions);
        verify(questionRepository).findAll();
    }

    @DisplayName("GET empty list test")
    @Test
    public void getEmptyListOfQuestionsTest(){
        given(questionRepository.findAll()).willReturn(Collections.emptyList());
        List<Question> questionList = questionService.getAllQuestions(null);
        assertThat(questionList).isEmpty();
    }

    @DisplayName("Get question by id test")
    @Test
    public void getQuestionByIdTest(){
        given(questionRepository.findById(question.getId())).willReturn(Optional.of(question));
        Question getQuestion = questionService.getQuestionById(question.getId());
        assertNotNull(getQuestion);
    }

    @DisplayName("Get no question with id test")
    @Test
    public void getQuestionByNoExistIdTest(){
        given(questionRepository.findById(anyLong())).willThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> questionService.getQuestionById(question.getId()));
    }

    @DisplayName("POST test")
    @Test
    public void addQuestionTest(){
        given(questionRepository.save(question)).willReturn(question);
        questionService.addQuestion(question);
        verify(questionRepository).save(question);
    }

    @DisplayName("PUT test")
    @Test
    public void updateQuestionTest(){

        Question newQuestion = new Question();
        newQuestion.setId(question.getId());
        newQuestion.setQuestionTitle("Test_2 Question Title");
        newQuestion.setCategory("Test_2 Category");
        newQuestion.setDifficultylevel(DifficultyLevel.EASY);
        newQuestion.setOption1("Test_2 Option 1");
        newQuestion.setOption2("Test_2 Option 2");
        newQuestion.setOption3("Test_2 Option 3");
        newQuestion.setOption4("Test_2 Option 4");
        newQuestion.setRightAnswer("Test_2 Right Answer");

        given(questionRepository.findById(question.getId())).willReturn(Optional.of(question));
        questionService.updateQuestion(newQuestion, question.getId());
        verify(questionRepository).save(question);
    }

    @DisplayName("DELETE test")
    @Test
    public void deleteQuestionWhichNotExistInQuizTest(){
        given(quizRepository.findByQuestionsId(question.getId())).willReturn(Collections.emptyList());
        willDoNothing().given(questionRepository).deleteById(question.getId());
        questionService.deleteQuestion(question.getId());
        verify(questionRepository).deleteById(question.getId());
    }

    @DisplayName("CheckAnswer method test")
    @Test
    public void checkAnswerTest(){
        given(questionRepository.findById(question.getId())).willReturn(Optional.of(question));
        boolean res = questionService.checkAnswer(question.getId(), 1);
        assertTrue(res);
    }
    @DisplayName("CheckAnswer incorrect option test")
    @Test
    public void checkIncorrectAnswerTest(){
        given(questionRepository.findById(question.getId())).willReturn(Optional.of(question));
        boolean res = questionService.checkAnswer(question.getId(), 2);
        assertFalse(res);
    }
    @DisplayName("CheckAnswer no exist option test")
    @Test
    public void checkNoExistOptionTest(){
        given(questionRepository.findById(question.getId())).willReturn(Optional.of(question));
        assertThrows(NotFoundException.class, ()->{
            questionService.checkAnswer(question.getId(), anyInt());
        });
    }
}

