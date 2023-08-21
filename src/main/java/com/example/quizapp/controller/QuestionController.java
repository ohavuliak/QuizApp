package com.example.quizapp.controller;

import com.example.quizapp.dto.PageDTO;
import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.mapper.PageMapper;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.service.QuestionService;
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
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final PageMapper pageMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping()
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(@RequestParam(required = false) String category) {
        log.info("Fetching list of questions");
        return ResponseEntity.ok(questionMapper.toListDto(questionService.getAllQuestions(category)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        log.info("Adding a new question");
        return ResponseEntity.ok(questionMapper.toDto(questionService.addQuestion(questionMapper.toEntity(questionDTO))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.info("Deleting question with ID: {}", id);
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestions(@RequestBody QuestionDTO questionDTO, @PathVariable Long id) {
        log.info("Updating question with ID: {}", id);
        return ResponseEntity.ok(questionService.updateQuestion(questionMapper.toEntity(questionDTO), id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}/{option}")
    public ResponseEntity<Boolean> checkAnswer(@PathVariable Long id, @PathVariable Integer option) {
        log.info("Checking answer for question with ID: {}", id);
        return ResponseEntity.ok(questionService.checkAnswer(id, option));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/pageable")
    public ResponseEntity<PageDTO<QuestionDTO>> getCategoryPageable(
            @RequestParam(required = false) String category, Pageable pageable) {
        log.info("Fetching pageable questions for category: {}", category);
        return ResponseEntity.ok(pageMapper.toPageDto(questionService.getCategoryPageable(category, pageable)
                .map(questionMapper::toDto)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        log.info("Fetching question with ID: {}", id);
        return ResponseEntity.ok(questionMapper.toDto(questionService.getQuestionById(id)));
    }
}