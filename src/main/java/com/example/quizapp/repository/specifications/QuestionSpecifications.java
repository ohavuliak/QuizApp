package com.example.quizapp.repository.specifications;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.Question_;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecifications {
    public static Specification<Question> hasSomeDifficultyLevelEasy(String difficultyLevel){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Question_.DIFFICULTYLEVEL), difficultyLevel));
    }

    public static Specification<Question> titleContains(String title){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Question_.QUESTION_TITLE)), "%" + title.toLowerCase() + "%");
    }


}
