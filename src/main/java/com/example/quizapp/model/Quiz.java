package com.example.quizapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(
                    name = "quiz_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "questions_id", referencedColumnName = "id")
    )
    @NotEmpty
    private List<Question> questions;
}
