package com.example.quizapp.controller;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.mapper.QuizMapper;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.QuizRequest;
import com.example.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizMapper quizMapper;

    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequest request) {
        return new ResponseEntity<>(quizService.createQuiz(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
        return ResponseEntity.ok(quizService.getQuizQuestions(id));
    }
    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(){
        return ResponseEntity.ok(quizService.getAllQuizzes().stream()
                .map(quiz -> quizMapper.quizToQuizDTO(quiz))
                .collect(Collectors.toList()));
    }
}
