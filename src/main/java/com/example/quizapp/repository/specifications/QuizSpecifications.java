package com.example.quizapp.repository.specifications;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.Question_;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Quiz_;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


public class QuizSpecifications{

    public static Specification<Quiz> hasAtLeastNumberOfQuestions(int num){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(root.get(Quiz_.questions)), num);
    }


    public static Specification<Quiz> titleContains(String word) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Quiz_.title)), "%" + word.toLowerCase() + "%");
    }
}
