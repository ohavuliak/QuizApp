package com.example.quizapp.repository.impl;

import com.example.quizapp.model.Answer;
import com.example.quizapp.repository.AnswerRepositoryCustom;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


public class AnswerRepositoryImpl implements AnswerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void customSave(Answer answer){
        entityManager.persist(answer);
    }

    public Integer countCorrectAnswers(String username) {
        String jpql = "SELECT COUNT(a) FROM Answer a WHERE a.username = :username AND a.checker = true";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("username", username);

        Long count = query.getSingleResult();
        return count.intValue();
    }

    @Override
    public Integer countIncorrectAnswers(String username) {
        String jpql = "SELECT COUNT(a) FROM Answer a WHERE a.username = :username AND a.checker = false";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("username", username);

        Long count = query.getSingleResult();
        return count.intValue();
    }

    @Override
    public Answer findAnswerById(Long id) {
        return entityManager.find(Answer.class, id);
    }

    @Override
    @Transactional
    public void deleteAnswerById(Long id) {
        Answer answer = entityManager.find(Answer.class, id);
        entityManager.remove(answer);
    }

    @PostConstruct
    public void postConstruct() {
        Objects.requireNonNull(entityManager);
    }
}
