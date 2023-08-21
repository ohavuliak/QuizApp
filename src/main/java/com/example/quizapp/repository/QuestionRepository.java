package com.example.quizapp.repository;

import com.example.quizapp.model.DifficultyLevel;
import com.example.quizapp.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor {
    List<Question> findByCategory(String category);
    @Query(value = "SELECT * from question q WHERE q.category=:category AND q.difficultylevel=:difficultylevel ORDER BY Random() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategoryAndDifficultylevel(String category, Integer numQ, DifficultyLevel difficultylevel);

    Page<Question> findByCategory(String category, Pageable pageable);

}
