package com.example.quizapp.security.mapper;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.mapper.EntityMapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.security.model.User;
import com.example.quizapp.security.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserMapper{
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "role", source = "user.role")

    UserDTO toUserDTO(User user);
    @Mapping(target = "id", source = "users.id")
    @Mapping(target = "email", source = "users.email")
    @Mapping(target = "firstName", source = "users.firstName")
    @Mapping(target = "lastName", source = "users.lastName")
    @Mapping(target = "role", source = "users.role")
    List<UserDTO> toListDTO(List<User> users);

}
