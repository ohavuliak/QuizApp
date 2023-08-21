package com.example.quizapp.controller;

import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.mapper.QuizMapper;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class QuizController {
    @Qualifier("withSpecifications")
    @Autowired
    QuizService quizService;
    private final QuizMapper quizMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequest request) {
        log.info("Adding a new quiz");
        return new ResponseEntity<>(quizService.createQuiz(request), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
        log.info("Fetching list of quiz's questions");
        return ResponseEntity.ok(quizService.getQuizQuestions(id));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(){
        log.info("Fetching list of quizzes");
        return ResponseEntity.ok(quizMapper.toListDto(quizService.getAllQuizzes()));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id){
        log.info("Deleting quiz with with ID: {}", id);
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@RequestBody QuizDTO quizDTO, @PathVariable Long id) {
        log.info("Updating question with ID: {}", id);
        return ResponseEntity.ok(quizService.updateQuiz(quizMapper.toEntity(quizDTO), id));
    }
}
