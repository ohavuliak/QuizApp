package com.example.quizapp.controller;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.dto.UserAnswerDTO;
import com.example.quizapp.mapper.QuizMapper;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.dao.request.QuizRequest;
import com.example.quizapp.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/resource/quiz")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Quiz", description = "The Quiz API. Contains all the operations that can be performed on a quiz.")
public class QuizController {
    @Qualifier("withSpecifications")
    @Autowired
    QuizService quizService;
    private final QuizMapper quizMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add quiz with random questions")
    @PostMapping()
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizRequest request) {
        log.info("Adding a new quiz");
        return ResponseEntity.ok(quizMapper.toDto(quizService.createQuiz(request)));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch list of quiz's questions")
    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
        log.info("Fetching list of quiz's questions");
        return ResponseEntity.ok(quizService.getQuizQuestions(id));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Fetch list of quizzes")
    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(){
        log.info("Fetching list of quizzes");
        return ResponseEntity.ok(quizMapper.toListDto(quizService.getAllQuizzes()));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete quiz")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id){
        log.info("Deleting quiz with with ID: {}", id);
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update quiz")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@RequestBody QuizDTO quizDTO, @PathVariable Long id) {
        log.info("Updating question with ID: {}", id);
        return ResponseEntity.ok(quizService.updateQuiz(quizMapper.toEntity(quizDTO), id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Submit quiz")
    @PostMapping("/{id}/submit")
    public ResponseEntity<String> submitQuizAnswer(@PathVariable Long id, @RequestBody List<UserAnswerDTO> userAnswers){
        return ResponseEntity.ok(quizService.submitQuizAnswers(id, userAnswers));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create quiz with question")
    @PostMapping("/add")
    public ResponseEntity<QuizDTO> addQuizWithQuestions(@RequestBody QuizDTO quizDTO){
        return ResponseEntity.ok(quizMapper.toDto(quizService.createQuizWithQuestions( quizMapper.toEntity(quizDTO))));
    }

}
