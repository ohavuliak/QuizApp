package com.example.quizapp.repository;

import com.example.quizapp.model.Feedback;
import com.example.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query(value = "SELECT f from Feedback f where f.quiz=:quiz")
    List<Feedback> findByQuizId(@Param("quiz") Quiz quiz);
}
