package com.example.quizapp.mapper;

import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionDTO  questionToQuestionDTO(Question question);
    Question questionDTOtoQuestion(QuestionDTO questionDTO);

}