package com.example.quizapp.mapper;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.model.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizDTO quizToQuizDTO(Quiz quiz);
    Quiz quizDTOtoQuiz(QuizDTO quizDTO);

}
