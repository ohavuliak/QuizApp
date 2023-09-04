package com.example.quizapp.dao.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequest {
    private Long id;
    private String title;
    private List<String> tags;
    private String text;
}
