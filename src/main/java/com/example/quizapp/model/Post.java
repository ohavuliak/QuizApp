package com.example.quizapp.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document
@Data
public class Post {
    @Id
    private Long id;
    private String title;
    private List<String> tags;
    private String text;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
