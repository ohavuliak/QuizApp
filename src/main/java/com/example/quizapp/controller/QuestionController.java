package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping(value = "pageable")
    public ResponseEntity<Map<String, Object>> getQuestionsPageable(@RequestParam(defaultValue = "0") final Integer pageNumber, @RequestParam(defaultValue = "5") final Integer size){
        return ResponseEntity.ok(questionService.getPageableQuestions(pageNumber, size));
    }
}
