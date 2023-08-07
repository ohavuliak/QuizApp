package com.example.quizapp.mapper;

import com.example.quizapp.dto.QuizDTO;
import com.example.quizapp.model.Quiz;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizDTO quizToQuizDTO(Quiz quiz);
    Quiz quizDTOtoQuiz(QuizDTO quizDTO);

    //Page<Quiz> pageQuizToPageQuizDTO(Page<QuizDTO> quizDTOPage);
    //Page<QuizDTO> pageQuizToPageQuizDTO(Page<Quiz> quizDTOPage);
}
