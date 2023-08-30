package com.example.quizapp.service.impl;

import com.example.quizapp.dao.request.AvatarRequest;
import com.example.quizapp.mapper.UserMapper;
import com.example.quizapp.model.Avatar;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.AvatarRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.model.User;
import com.example.quizapp.dto.UserDTO;
import com.example.quizapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final AvatarRepository avatarRepository;
    private final UserMapper userMapper;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by("email"));
    }

    @Override
    public User getAllAdmins() {
        return userRepository.findAllAdmins();
    }

    @Override
    public Map<UserDTO, List<Quiz>> getQuizForUser(Long id, String title) {
        HashMap<UserDTO, List<Quiz>> map = new HashMap<>();
        map.put(userMapper.toUserDTO(userRepository.findUserById(id)) , quizRepository.findByTitle(title) );
        return map;
    }

    @Override
    public Page<User> getUsersPageable(Pageable pageable) {
        return userRepository.findAllUsersWithPagination(pageable);
    }

    @Override
    public List<User> getSortedByNameList() {
        return userRepository.getSortedUsersByName();
    }

    @Override
    public List<User> getAllUsersWithAvatar() {
        return userRepository.findAllUsersWithAvatar();
    }

}
