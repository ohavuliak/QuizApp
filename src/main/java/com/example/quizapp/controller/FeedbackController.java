package com.example.quizapp.controller;

import com.example.quizapp.dao.request.FeedbackRequest;
import com.example.quizapp.dto.FeedbackDTO;
import com.example.quizapp.mapper.FeedbackMapper;
import com.example.quizapp.model.Feedback;
import com.example.quizapp.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource/feedback")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Feedback", description = "The Feedback API. Contains all the operations that can be performed on a feedback.")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Send feedback")
    @PostMapping()
    public ResponseEntity<FeedbackDTO> addFeedback(@RequestBody FeedbackRequest feedbackRequest){
        return ResponseEntity.ok(feedbackMapper.toDto(feedbackService.addFeedback(feedbackRequest)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Show quiz's feedbacks")
    @GetMapping("/{id}")
    public ResponseEntity<List<FeedbackDTO>> getQuizFeedbacks(@PathVariable Long id){
        return ResponseEntity.ok(feedbackMapper.toListDto(feedbackService.getQuizFeedback(id)));
    }
}
