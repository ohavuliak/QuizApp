package com.example.quizapp.controller;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.mapper.QuizMapper;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/resource/quiz")
@RequiredArgsConstructor
public class QuizController {
    @Qualifier("withSpecifications")
    @Autowired
    QuizService quizService;
    private final QuizMapper quizMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequest request) {
        return new ResponseEntity<>(quizService.createQuiz(request), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
        return ResponseEntity.ok(quizService.getQuizQuestions(id));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(){
        return ResponseEntity.ok(quizMapper.toListDto(quizService.getAllQuizzes()));

    }
}
