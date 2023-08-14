package com.example.quizapp.mapper;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.model.Quiz;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizMapper extends  EntityMapper<Quiz, QuizDTO> {
}
