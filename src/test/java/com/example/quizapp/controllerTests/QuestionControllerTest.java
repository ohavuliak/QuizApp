package com.example.quizapp.controllerTests;

import com.example.quizapp.controller.QuestionController;
import com.example.quizapp.model.DifficultyLevel;
import com.example.quizapp.security.dao.response.JwtAuthenticationResponse;
import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.mapper.PageMapper;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.security.service.JwtService;
import com.example.quizapp.service.QuestionService;
import com.example.quizapp.service.UserService;
import com.example.quizapp.security.service.impl.JwtServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @MockBean
    QuestionService questionService;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    PageMapper pageMapper;
    @MockBean
    UserService userService;
    @Mock
    private UserDetails userDetails;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    QuestionMapper questionMapper;
    QuestionDTO questionDTO;
    Question question;

    List<Question> questionList;
    List<QuestionDTO> questionDTOList;

    @BeforeEach
    public void setup() {

        questionDTO = new QuestionDTO();
        question = new Question();
        questionList = new ArrayList<>();
        questionDTOList = new ArrayList<>();

        question.setId(1L);
        question.setQuestionTitle("Test Question Title");
        question.setCategory("Test Category");
        question.setDifficultylevel(DifficultyLevel.EASY);
        question.setOption1("Test Option 1");
        question.setOption2("Test Option 2");
        question.setOption3("Test Option 3");
        question.setOption4("Test Option 4");
        question.setRightAnswer("Test Option 3");

        questionList.add(question);
        questionDTO = questionMapper.toDto(question);

        questionDTOList = questionMapper.toListDto(questionList);

        final String jwtSigningKey = "UoUadBpef4zpK5WhPVnitMIhqYbQNasAzPcTX5hMU85m/MRivALw4quXIV7JRDQh";
        MockitoAnnotations.openMocks(this);
        when(userDetails.getUsername()).thenReturn("testUser");
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        jwtService = mock(JwtServiceImpl.class);

        when(jwtService.generateToken(userDetails)).thenCallRealMethod();
        when(jwtService.getSigningKey()).thenReturn(Keys.hmacShaKeyFor(keyBytes));
    }


    @Test
    @WithMockUser(username = "testUser")
    @DisplayName("Test. GET question by ID")
    public void getQuestionById() throws Exception {
        String generatedToken = jwtService.generateToken(userDetails);

        when(questionService.getQuestionById(question.getId())).thenReturn(question);
        when(questionMapper.toDto(question)).thenReturn(questionDTO);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/resource/question/{id}", 1)
                        .header("Authorization", "Bearer " + generatedToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Convert the response content to a QuestionDTO using objectMapper
        QuestionDTO responseQuestionDTO = objectMapper.readValue(response.getContentAsString(), QuestionDTO.class);

        // Assert
        assertEquals(questionDTO, responseQuestionDTO);
        System.out.println("Expected JSON: " + objectMapper.writeValueAsString(questionDTO));
        System.out.println("Actual JSON: " + response.getContentAsString());
    }


    @WithMockUser("testUser")
    @DisplayName("Test. GET all questions")
    @Test
    public void getAllQuestion() throws Exception{
        String generatedToken = jwtService.generateToken(userDetails);

        when(questionService.getAllQuestions(null)).thenReturn(questionList);
        questionList = questionService.getAllQuestions(null);
        when(questionMapper.toListDto(questionList)).thenReturn(questionDTOList);
        questionDTOList = questionMapper.toListDto(questionList);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(generatedToken);
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/v1/resource/question")
                                .header("Authorization", "Bearer "+ jwtAuthenticationResponse.getToken())
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
        assertEquals(objectMapper.writeValueAsString(questionDTOList), response.getContentAsString());
        System.out.println("TEST GET ALL : \n" +response.getContentAsString());
    }
}