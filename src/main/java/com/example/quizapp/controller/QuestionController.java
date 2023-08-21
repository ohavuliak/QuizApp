package com.example.quizapp.controller;

import com.example.quizapp.dto.PageDTO;
import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.mapper.PageMapper;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/resource/question")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Question", description = "The Question API. Contains all the operations that can be performed on a question.")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final PageMapper pageMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch list of questions")
    @GetMapping()
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(@RequestParam(required = false) String category) {
        log.info("Fetching list of questions");
        return ResponseEntity.ok(questionMapper.toListDto(questionService.getAllQuestions(category)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add question")
    @PostMapping()
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        log.info("Adding a new question");
        return ResponseEntity.ok(questionMapper.toDto(questionService.addQuestion(questionMapper.toEntity(questionDTO))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete question")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.info("Deleting question with ID: {}", id);
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update question")
    public ResponseEntity<String> updateQuestions(@RequestBody QuestionDTO questionDTO, @PathVariable Long id) {
        log.info("Updating question with ID: {}", id);
        return ResponseEntity.ok(questionService.updateQuestion(questionMapper.toEntity(questionDTO), id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}/{option}")
    @Operation(summary = "Check answer")
    public ResponseEntity<Boolean> checkAnswer(@PathVariable Long id, @PathVariable Integer option) {
        log.info("Checking answer for question with ID: {}", id);
        return ResponseEntity.ok(questionService.checkAnswer(id, option));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch pages of questions")
    @GetMapping(value = "/pageable")
    public ResponseEntity<PageDTO<QuestionDTO>> getCategoryPageable(
            @RequestParam(required = false) String category, Pageable pageable) {
        log.info("Fetching pageable questions for category: {}", category);
        return ResponseEntity.ok(pageMapper.toPageDto(questionService.getCategoryPageable(category, pageable)
                .map(questionMapper::toDto)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch questions by its id")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        log.info("Fetching question with ID: {}", id);
        return ResponseEntity.ok(questionMapper.toDto(questionService.getQuestionById(id)));
    }
}