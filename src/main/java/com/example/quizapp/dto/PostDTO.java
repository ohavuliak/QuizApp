package com.example.quizapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private List<String> tags;
    private String text;
    private LocalDateTime createdAt;
}
