package com.example.quizapp.repository;

import com.example.quizapp.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(String category);
    @Query(value = "SELECT * from question q WHERE q.category=:category ORDER BY Random() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, Integer numQ);

    Page<Question> findByCategory(String category, Pageable pageable);

}
