package com.example.quizapp.controller;

import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping()
    public ResponseEntity<List<Question>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Question>>  getQuestionsCategory(@PathVariable String category){
        return ResponseEntity.ok(questionService.getQuestionsByCategory(category));
    }

    @PostMapping()
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        return ResponseEntity.ok(questionService.addQuestion(question));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestions(@RequestBody Question question, @PathVariable Long id){
        return ResponseEntity.ok(questionService.updateQuestion(question, id));
    }

    @GetMapping("/{id}/{option}")
    public ResponseEntity<Boolean> checkAnswer(@PathVariable Long id, @PathVariable Integer option){
        return ResponseEntity.ok(questionService.checkAnswer(id, option));
    }

    @GetMapping(value = "/pageable")
    public ResponseEntity<Page<Question>> getCategoryPageable(@RequestParam(required = false) String category, Pageable pageable){
        return ResponseEntity.ok(questionService.getCategoryPageable(category, pageable));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id){
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }
}
