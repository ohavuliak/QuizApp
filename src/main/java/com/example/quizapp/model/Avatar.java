package com.example.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
}
