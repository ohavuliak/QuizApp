package com.example.quizapp.mapper;

import com.example.quizapp.dto.FeedbackDTO;
import com.example.quizapp.model.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper extends EntityMapper<Feedback, FeedbackDTO>{
}
