package com.example.quizapp.controller;

import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.mapper.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping()
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions().stream()
                .map(question -> questionMapper.questionToQuestionDTO(question))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<QuestionDTO>>  getQuestionsCategory(@PathVariable String category){
        return ResponseEntity.ok(questionService.getQuestionsByCategory(category).stream()
                .map(question -> questionMapper.questionToQuestionDTO(question))
                .collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO){
        return ResponseEntity.ok(questionMapper.questionToQuestionDTO(
                questionService.addQuestion(
                        questionMapper.questionDTOtoQuestion(questionDTO)
                )));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestions(@RequestBody QuestionDTO questionDTO, @PathVariable Long id){
        return ResponseEntity.ok(questionService.updateQuestion(questionMapper.questionDTOtoQuestion(questionDTO), id));
    }

    @GetMapping("/{id}/{option}")
    public ResponseEntity<Boolean> checkAnswer(@PathVariable Long id, @PathVariable Integer option){
        return ResponseEntity.ok(questionService.checkAnswer(id, option));
    }


    @GetMapping(value = "/pageable")
    public ResponseEntity<Page<QuestionDTO>> getCategoryPageable(@RequestParam(required = false) String category, Pageable pageable){
        Page<Question> questionPage = questionService.getCategoryPageable(category, pageable);

        Page<QuestionDTO> questionDTOPage = new PageImpl<>(
                questionPage.getContent().stream()
                        .map(question -> questionMapper.questionToQuestionDTO(question))
                        .collect(Collectors.toList()),
                pageable,
                questionPage.getTotalElements()
        );
        return ResponseEntity.ok(questionDTOPage);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id){
        return ResponseEntity.ok(questionMapper.questionToQuestionDTO(questionService.getQuestionById(id)));
    }
}
