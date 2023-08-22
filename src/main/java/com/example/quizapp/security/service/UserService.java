package com.example.quizapp.security.service;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.security.model.User;
import com.example.quizapp.security.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;

public interface UserService {
    UserDetailsService userDetailsService();

    List<User> getAllUsers();

    User getAllAdmins();

    Map<UserDTO, List<Quiz>> getQuizForUser(Long id, String title);

    Page<User> getUsersPageable(Pageable pageable);

    List<User> getSortedByNameList();
}