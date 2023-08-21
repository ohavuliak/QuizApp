package com.example.quizapp.repository.specifications;

import com.example.quizapp.model.*;
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

    public static Specification<Quiz> sortByAmountOfQuestions(){
        return (root, query, criteriaBuilder) -> {
            root.join(Quiz_.questions);
            Order orderByAmountOfQuestions = criteriaBuilder.asc(criteriaBuilder.size(root.get(Quiz_.questions)));
            query.orderBy(orderByAmountOfQuestions);
            return null;
        };
    }

    public static Specification<Quiz> showOnlyQuizzesWithHardQuestions() {
        return (root, query, criteriaBuilder) -> {
            root.join(Quiz_.questions);

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Question> subRoot = subquery.from(Question.class);
            subquery.select(criteriaBuilder.count(subRoot))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(subRoot.get(Question_.difficultylevel), DifficultyLevel.HARD),
                            criteriaBuilder.isMember(subRoot, root.get(Quiz_.questions))
                    ));

            return criteriaBuilder.equal(subquery, criteriaBuilder.size(root.get(Quiz_.questions)));
        };
    }

}
