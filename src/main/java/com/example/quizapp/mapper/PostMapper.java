package com.example.quizapp.mapper;

import com.example.quizapp.dto.PostDTO;
import com.example.quizapp.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<Post, PostDTO> {
}
