package com.example.quizapp.serviceTests;

import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {
    @Mock
    QuizRepository quizRepository;
    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    QuizServiceImpl quizService;
    @Mock
    QuestionMapper questionMapper;

    private Quiz quiz;
    private QuizRequest quizRequest;

    @BeforeEach
    public void setup(){
        quizRequest = new QuizRequest();
        quizRequest.setTitle("Test Quiz");
        quizRequest.setCategory("Java");
        quizRequest.setNumQ(5);

        quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setQuestions(questionRepository.findRandomQuestionsByCategoryAndDifficultylevel(quizRequest.getCategory(), quizRequest.getNumQ(), quizRequest.getDifficultylevel()));
    }


    @DisplayName("Get all quiz questions test")
    @Test
    public void getQuizQuestionsTest(){
        given(quizRepository.findById(quiz.getId())).willReturn(Optional.ofNullable(quiz));
        List<QuestionWrapper> questionList = quizService.getQuizQuestions(quiz.getId());
        List<Question> expectedList = quiz.getQuestions();
        assertEquals(expectedList, questionList);
    }
    @DisplayName("Get all quizzes test")
    @Test
    public void getAllQuizzesTest(){
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        given(quizRepository.findAll()).willReturn(quizList);
        List<Quiz> expected = quizService.getAllQuizzes();
        assertEquals(expected, quizList);
        verify(quizRepository).findAll();
    }

    @DisplayName("Post Quiz")
    @Test
    public void addQuiz(){
        given(quizRepository.save(quiz)).willReturn(quiz);
        quizService.createQuiz(quizRequest);
        verify(quizRepository).save(quiz);
    }
}
