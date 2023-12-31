package com.example.quizapp.repository;

import com.example.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, JpaSpecificationExecutor {
    List<Quiz> findByQuestionsId(Long id);
    @Query(value = "SELECT q from Quiz q WHERE q.title like %:title%")
    List<Quiz> findByTitle(@Param("title") String title);
}
